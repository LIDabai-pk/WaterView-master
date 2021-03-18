package com.ltb.laer.waterview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ltb.laer.waterview.model.TreeRecord;
import com.ltb.laer.waterview.util.DataAccess;
import com.ltb.laer.waterview.view.TreeView2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TongjiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TongjiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private static final String TAG = "HomeFragment";
    private View layoutView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tv_count;
    private DataAccess dataAccess = new DataAccess(getActivity());
    private LineChart lineChart;
    private LineData lineData;
    private TreeView2 treeView;
    private int count = 0;
    //区间标题
    private String title;
    List<TreeRecord> list = new ArrayList<>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TongjiFragment newInstance(String param1, String param2) {
        TongjiFragment fragment = new TongjiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        }
    }

    /**
     * 初始化界面
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutView = inflater.inflate(R.layout.fragment_tongji, container, false);
        initConfig(layoutView);
        return layoutView;
    }

    /**
     * 初始化控件和数据
     *
     * @param layoutView
     */
    private void initConfig(View layoutView) {
        tv_count = layoutView.findViewById(R.id.tv_count);
        treeView = layoutView.findViewById(R.id.treeView);
        lineChart = layoutView.findViewById(R.id.lineChart);
        getData();
    }

    /**
     * @return
     */
    private void getData() {
        //查询
        String[] selectionArgs = new String[]{title};
        list = dataAccess.queryTreeRecord("during = ?", selectionArgs);
        StringBuilder sb = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                count += list.get(i).getNum();
                sb.append(list.get(i).getTotal());
            }
        }
        //树展示
        tv_count.setText(+ count + "  Planted");
        treeView.setPoint(count,inputChange2Drawable(sb.toString()));
        //统计图
        lineData = initSingleLineChart(list);
        initDataStyle(lineChart, lineData, getContext());
    }

    /**
     * 根据名称得到资源id
     * @param input
     * @return
     */
    private List<Integer> inputChange2Drawable(String input) {
        List<Integer> list = new ArrayList<>();
        char[] itemNum = null;
        Uri uri = null;
        if (input.length() > 0) {
            itemNum = input.toCharArray();
        }
        if (itemNum != null && itemNum.length > 0) {
            int pos = 0;
            for (int i = 0; i < itemNum.length; i++) {
                //拼接
                pos = itemNum[i] - '0';
                list.add(pos);
            }
        }
        return list;
    }

    /**
     * 根据名称获得资源
     * @param imageName
     * @return
     */
    private int getDrawByReflect(String imageName){
        try {
            Field field = Class.forName("cn.testreflect.R$drawable").getField(imageName);
            return field.getInt(field);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 创建一条折线
     *
     * @return LineData
     */
    public static LineData initSingleLineChart(List<TreeRecord> list) {

        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xValues.add(list.get(i).getTime().replace("2021-", ""));
        }

        // y轴的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < list.size(); i++) {
            yValues.add(new Entry(list.get(i).getNum(), i));
        }
        //设置折线的样式
        LineDataSet dataSet = new LineDataSet(yValues, "Tree planting line chart");
        //用y轴的集合来设置参数
        dataSet.setLineWidth(1.75f); // 线宽
        dataSet.setCircleSize(2f);// 显示的圆形大小
        dataSet.setColor(Color.rgb(89, 194, 230));// 折线显示颜色
        dataSet.setCircleColor(Color.rgb(89, 194, 230));// 圆形折点的颜色
        dataSet.setHighLightColor(Color.GREEN); // 高亮的线的颜色
        dataSet.setHighlightEnabled(true);
        dataSet.setValueTextColor(Color.rgb(89, 194, 230)); //数值显示的颜色
        dataSet.setValueTextSize(8f);     //数值显示的大小

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        //构建一个LineData  将dataSets放入
        LineData lineData = new LineData(xValues, dataSets);
        return lineData;
    }

    /**
     * @Description:初始化图表的样式
     */
    public static void initDataStyle(LineChart lineChart, LineData lineData, Context context) {
        //设置点击折线点时，显示其数值
//        MyMakerView mv = new MyMakerView(context, R.layout.item_mark_layout);
//        mLineChart.setMarkerView(mv);
        lineChart.setDrawBorders(false); //在折线图上添加边框
        //lineChart.setDescription("时间/数据"); //数据描述
        lineChart.setDrawGridBackground(false); //表格颜色
        lineChart.setGridBackgroundColor(Color.GRAY & 0x70FFFFFF); //表格的颜色，设置一个透明度
        lineChart.setTouchEnabled(true); //可点击
        lineChart.setDragEnabled(true);  //可拖拽
        lineChart.setScaleEnabled(true);  //可缩放
        lineChart.setPinchZoom(false);
        lineChart.setBackgroundColor(Color.WHITE); //设置背景颜色

        lineChart.setData(lineData);

        Legend mLegend = lineChart.getLegend(); //设置标示，就是那个一组y的value的
        mLegend.setForm(Legend.LegendForm.CIRCLE); //样式
        mLegend.setFormSize(6f); //字体
        mLegend.setTextColor(Color.GRAY); //颜色
        lineChart.setVisibleXRange(0, 4);   //x轴可显示的坐标范围
        XAxis xAxis = lineChart.getXAxis();  //x轴的标示
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x轴位置
        xAxis.setTextColor(Color.GRAY);    //字体的颜色
        xAxis.setTextSize(10f); //字体大小
        xAxis.setGridColor(Color.GRAY);//网格线颜色
        xAxis.setDrawGridLines(false); //不显示网格线
        YAxis axisLeft = lineChart.getAxisLeft(); //y轴左边标示
        YAxis axisRight = lineChart.getAxisRight(); //y轴右边标示
        axisLeft.setTextColor(Color.GRAY); //字体颜色
        axisLeft.setTextSize(10f); //字体大小
        //axisLeft.setAxisMaxValue(800f); //最大值
        axisLeft.setLabelCount(5, true); //显示格数
        axisLeft.setGridColor(Color.GRAY); //网格线颜色

        axisRight.setDrawAxisLine(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);

        //设置动画效果
        lineChart.animateY(2000, Easing.EasingOption.Linear);
        lineChart.animateX(2000, Easing.EasingOption.Linear);
        lineChart.invalidate();
        //lineChart.animateX(2500);  //立即执行动画
    }

}
