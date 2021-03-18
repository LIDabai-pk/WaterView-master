package com.ltb.laer.waterview.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TimeUtil {

    private static int mYear;
    private static int mMonth;
    private static int mNowDay;
    private static int mNextDay;
    private static String m_month;
    private static String m_day;

    /**
     * 获得当前时间延后 delay天的字符串数据
     * @param delay
     * @return
     */
    public static String StringData(int delay){

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码

        c.set(Calendar.DAY_OF_MONTH, mNowDay+delay); // 延后3天
        mNextDay = c.get(Calendar.DAY_OF_MONTH);
        // 判断延后的日期小于今天的日期，月份加一
        if(mNowDay>mNextDay){
            mMonth+=1;
        }
        // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
        if(mMonth>12){
            mMonth = 1;
            mYear +=1 ;
        }
        // 测试今天的日期
        Log.e("今日时间===>", mYear + "年"+ mMonth +"月" + mNowDay + "日");
        // 如果 月份为个位数则加个0在前面
        if(mMonth < 10){
            m_month = "0" + mMonth ;
        } else{
            m_month = "" + mMonth ;
        }
        // 如果 天数为个位数则加个0在前面
        if(mNextDay < 10){
            m_day = "0" + mNextDay ;
        }else{
            m_day = "" + mNextDay ;
        }
        return mYear+"-"+m_month + "-" + m_day;
    }

    /**
     * 获得最近7天的日期
     * @return
     */
    public static List<String> getList7days(){
        List<String> list_days = new ArrayList<>();
        String day = null;
        for (int i= 0;i< 7; i++){
            day = StringData(i);
            list_days.add(day);
        }
        return list_days;
    }

    /**
     * 日期转 字符串
     * @param date
     * @return
     */
    public static String DateToStr8(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }

}
