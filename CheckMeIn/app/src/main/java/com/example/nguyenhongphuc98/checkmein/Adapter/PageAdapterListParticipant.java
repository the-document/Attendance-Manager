package com.example.nguyenhongphuc98.checkmein.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nguyenhongphuc98.checkmein.CreateActivity.ViewListParticipantFragment;

public class PageAdapterListParticipant extends FragmentPagerAdapter {
    private int mNumOfTab;

    public PageAdapterListParticipant(FragmentManager fm, int numOfTab) {
        super(fm);
        this.mNumOfTab=numOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new ViewListParticipantFragment();
            case 1:
                return  new ViewListParticipantFragment();
            case 2:
                return  new ViewListParticipantFragment();
            default:
                return null;
        }
        //return new ViewListParticipantFragment();
    }

    @Override
    public int getCount() {
        return this.mNumOfTab;
    }
}
