package com.example.nguyenhongphuc98.checkmein.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nguyenhongphuc98.checkmein.CreateActivity.ViewListActivityFragment;
import com.example.nguyenhongphuc98.checkmein.CreateActivity.ViewSetContentFragment;
import com.example.nguyenhongphuc98.checkmein.CreateActivity.ViewSetNameFragment;
import com.example.nguyenhongphuc98.checkmein.CreateActivity.ViewSetTimeFragment;

public class PageAdapterListActivity extends FragmentPagerAdapter {

    private int mNumOfTab;

    public PageAdapterListActivity(FragmentManager fm, int numOfTab){
        super(fm);
        this.mNumOfTab=numOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  ViewListActivityFragment.GetInstance();
            case 1:
                return  ViewSetNameFragment.GetInstance();
            case 2:
                return  ViewSetTimeFragment.GetInstance();
            case 3:
                return  ViewSetContentFragment.GetInstance();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mNumOfTab;
    }
}
