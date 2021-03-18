package com.ltb.laer.waterview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ltb.laer.waterview.model.TreeRecord;
import com.ltb.laer.waterview.model.Water;
import com.ltb.laer.waterview.util.Constant;
import com.ltb.laer.waterview.util.DataAccess;
import com.ltb.laer.waterview.util.SPUtils;
import com.ltb.laer.waterview.util.TimeUtil;
import com.ltb.laer.waterview.view.WaterView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class EnergyActivity extends AppCompatActivity {
    public static final String cate_0 = "0";
    public static final String cate_1 = "1";
    public static final String cate_2 = "2";
    public static final String cate_3 = "3";
    private WaterView mWaterView;
    private List<Water> mWaters = new ArrayList<>();
    private List<TreeRecord> list = new ArrayList<>();
    private DataAccess dataAccess = new DataAccess(this);
    private int totalWater;

    {
        for (int i = 0; i < 10; i++) {
            mWaters.add(new Water((int) (i + Math.random() * 4), "item" + i));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mWaterView = findViewById(R.id.wv_water);
        mWaterView.setWaters(mWaters);
        totalWater = (int) SPUtils.get(this,Constant.sp_sum,0);
        dealWaterData(totalWater);

        final ImageButton ibtnForest = findViewById(R.id.ibtnForest);
        ibtnForest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnergyActivity.this, ForestActivity.class);
                startActivity(intent);
            }

        });

        final ImageButton ibtnPlus = findViewById(R.id.ibtnPlus);

        ibtnPlus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnergyActivity.this,ChooseActivity.class);
                intent.putExtra("total",mWaterView.getmTotalConsumeWater());
                startActivity(intent);
            }
        });
        //查询数据
        queryData();
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

    @Subscriber(tag = "deal_total_warter")
    public void dealWaterData(int data){
        totalWater = data;
        mWaterView.setmTotalConsumeWater(totalWater);
        TextView sum = (TextView) findViewById(R.id.sum);
        sum.setText("Sum:" + String.valueOf(totalWater));
        SPUtils.put(this, Constant.sp_sum,totalWater);
    }



    public void onRest(View view) {
        mWaterView.setWaters(mWaters);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
