package com.example.alpha_shiftmanagement.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.alpha_shiftmanagement.R;

public class SecondActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG,"onCreate: Started.");

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.container);
        //setup the pager
        setupViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(),"Fragment1-ChangeName");
        adapter.addFragment(new Fragment2(),"Fragment2-ChangeName");  // i can change to order !!!
        adapter.addFragment(new Fragment3(),"Fragment3-ChangeName");
        viewPager.setAdapter(adapter);
    }

    public void setmViewPager(int fragmentNumber){    // move threw fragments
        mViewPager.setCurrentItem(fragmentNumber);
    }
}