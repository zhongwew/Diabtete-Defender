package com.example.diabetedefender;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager myVPs;

    private FragmentPagerAdapter fpAdapter;

    private List<Fragment> pageFrags;

    private BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    myVPs.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    myVPs.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    myVPs.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    private void initData(){
        pageFrags = new ArrayList<>();
        pageFrags.add(new Homepage());
        pageFrags.add(new Recommpage());
        pageFrags.add(new Myinfopage());

        //init the adapter
        fpAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                //return the fragment at corresponding position
                return pageFrags.get(i);
            }

            @Override
            public int getCount() {
                return pageFrags.size();
            }
        };
        //set the viewpager adapter
        myVPs.setAdapter(fpAdapter);

        //listen the view change of viewpager
        myVPs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                myVPs.setCurrentItem(i);
                switch (i){
                    case 0:
                        navigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.navigation_notifications);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myVPs = (ViewPager) findViewById(R.id.viewpager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initData();

    }



}
