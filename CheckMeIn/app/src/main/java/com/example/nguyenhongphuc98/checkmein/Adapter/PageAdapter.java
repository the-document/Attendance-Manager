package com.example.nguyenhongphuc98.checkmein.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nguyenhongphuc98.checkmein.InfoFragmentAccount;
import com.example.nguyenhongphuc98.checkmein.InfoFragmentAct;

public class PageAdapter extends FragmentPagerAdapter {

    private int mNumOfTab;
    InfoFragmentAct act;
    InfoFragmentAccount account;

    public PageAdapter(FragmentManager fm,int numOfTab){
        super(fm);

        this.mNumOfTab=numOfTab;
        act=InfoFragmentAct.GetInstance();
        account=InfoFragmentAccount.GetInstance();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  act;
            case 1:
                return  account;

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return this.mNumOfTab;
    }
}
