package com.ltb.laer.waterview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ltb.laer.waterview.model.TreeRecord;
import com.ltb.laer.waterview.util.DataAccess;

import java.util.ArrayList;
import java.util.List;

public class ForestActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView tv_during;
    List<TreeRecord> list = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    private DataAccess dataAccess = new DataAccess(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forest);
        viewPager = findViewById(R.id.viewPager);
        tv_during = findViewById(R.id.tv_during);
        getData();
    }

    private void getData(){
        //获得所有时间区间
        list = dataAccess.queryTreeRecord(null, null);
        for (int i = 0 ;i < list.size();i++){
            for (int j = list.size() - 1 ; j > i; j--)  //内循环是 外循环一次比较的次数
            {

                if (list.get(i).getDuring().equals(list.get(j).getDuring()))
                {
                    list.remove(j);
                }
            }
        }
        for (int i = 0;i< list.size();i++){
            titles.add(list.get(i).getDuring());
        }
        if (titles.size()> 0) {
            tv_during.setText("<   " + titles.get(0) + "   >");
        }
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_during.setText("< "+titles.get(position)+" >");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    public class MyPagerAdapter extends FragmentPagerAdapter
    {
        private Fragment pageFragment;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
           return TongjiFragment.newInstance(titles.get(position),null);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
        @Override
        public int getCount() {
            return titles.size();
        }
    }
}
