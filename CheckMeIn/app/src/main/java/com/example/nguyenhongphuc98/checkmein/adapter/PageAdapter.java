package com.example.nguyenhongphuc98.checkmein.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nguyenhongphuc98.checkmein.InfoFragmentAccount;
import com.example.nguyenhongphuc98.checkmein.InfoFragmentAct;

public class PageAdapter extends FragmentPagerAdapter {

    private int mNumOfTab;

    public PageAdapter(FragmentManager fm,int numOfTab){
        super(fm);
        this.mNumOfTab=numOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new InfoFragmentAct();
            case 1:
                return  new InfoFragmentAccount();

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return this.mNumOfTab;
    }
}
