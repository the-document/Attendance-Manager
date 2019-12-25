package com.example.nguyenhongphuc98.checkmein.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nguyenhongphuc98.checkmein.UI.user.account.InfoFragmentAccount;
import com.example.nguyenhongphuc98.checkmein.UI.user.activity.InfoFragmentAct;

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
                return  InfoFragmentAct.Instance();
            case 1:
                return  InfoFragmentAccount.Instance();

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return this.mNumOfTab;
    }
}
