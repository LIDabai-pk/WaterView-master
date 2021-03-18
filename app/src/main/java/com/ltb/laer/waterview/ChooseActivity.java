package com.ltb.laer.waterview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ltb.laer.waterview.model.TreeRecord;
import com.ltb.laer.waterview.util.DataAccess;
import com.ltb.laer.waterview.util.TimeUtil;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener {
    DataAccess dataAccess = new DataAccess(this);
    private RecyclerView rv_list;
    private CardView cardView0, cardView1, cardView2, cardView3;
    private List<TreeRecord> list = new ArrayList<>();
    String cate = "0";
    private int totalWater = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalWater = getIntent().getIntExtra("total", 0);
        setContentView(R.layout.activity_choose);
        cardView0 = findViewById(R.id.card_view0);
        cardView0.setOnClickListener(this);
        cardView1 = findViewById(R.id.card_view1);
        cardView1.setOnClickListener(this);
        cardView2 = findViewById(R.id.card_view2);
        cardView2.setOnClickListener(this);
        cardView3 = findViewById(R.id.card_view3);
        cardView3.setOnClickListener(this);
        queryData();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_view0:
                cate = "0";
                break;
            case R.id.card_view1:
                cate = "1";
                break;
            case R.id.card_view2:
                cate = "2";
                break;
            case R.id.card_view3:
                cate = "3";
                break;
        }
        showingDialog(cate);
    }


    /**
     * 查询当前数据库是否有任何数据
     */
    private void queryData() {
        list = dataAccess.queryTreeRecord(null, null);
        if (list.size() == 0) {
            insertListData(0, "");
        }
    }

    /**
     * 插入最近7日数据
     */
    private void insertListData(int num, String cate) {
        TreeRecord treeRecord = null;
        List<String> days = TimeUtil.getList7days();
        String during = days.get(0) + " to " + days.get(days.size() - 1);
        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                //插入1颗
                treeRecord = new TreeRecord(days.get(i), 0, num, cate, during);
            } else {
                //空数据
                treeRecord = new TreeRecord(days.get(i), 0, 0, "", during);
            }

            dataAccess.insertUser(treeRecord);
        }
    }

    /**
     * 种树
     */
    private void insertTreeData(String cate) {
        String day = TimeUtil.DateToStr8(new Date());//得到今日
        String[] selectionArgs = new String[]{day};
        list = dataAccess.queryTreeRecord("time = ?", selectionArgs);
        if (list.size() > 0) {
            //数据库能查到，更新数目
            TreeRecord data = list.get(0);
            data.setNum(data.getNum() + 1);
            data.setTotal(data.getTotal() + cate);
            dataAccess.updateTreeRecord(data);
        } else {
            //查不到表示日期还没录入数据
            insertListData(1, cate);
        }

    }


    public void showNotEnough(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage(str);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void showingDialog(final String cate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Would you like to spend 50 Energy to plant a Fitree ?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (totalWater >= 50) {
                    //插入数据
                    insertTreeData(cate);
                    totalWater -= 50;
                    showNotEnough("You have planted a tree!");
                    EventBus.getDefault().post(totalWater, "deal_total_warter");
                    Intent intent = new Intent(ChooseActivity.this,ForestActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showNotEnough("Not enough Energy.");
                }

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}