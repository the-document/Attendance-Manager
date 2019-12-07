package com.example.nguyenhongphuc98.checkmein.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nguyenhongphuc98.checkmein.UI.event.ViewListActivityFragment;
import com.example.nguyenhongphuc98.checkmein.UI.event.ViewSetLocationFragment;
import com.example.nguyenhongphuc98.checkmein.UI.event.ViewSetNameFragment;
import com.example.nguyenhongphuc98.checkmein.UI.event.ViewSetTimeFragment;

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
                return  new ViewListActivityFragment();
            case 1:
                return  new ViewSetNameFragment();
            case 2:
                return  new ViewSetTimeFragment();
            case 3:
                return  new ViewSetLocationFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mNumOfTab;
    }
}
