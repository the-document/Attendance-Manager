package com.example.nguyenhongphuc98.checkmein.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nguyenhongphuc98.checkmein.UI.event_list_join.ParticipantListFragment;
import com.example.nguyenhongphuc98.checkmein.UI.event_ans.ParticipantAnalyticsListFragment;
import com.example.nguyenhongphuc98.checkmein.UI.event_ques.listing_question.QuestionListFragment;

public class QuestionManagerViewPagerAdapter extends FragmentPagerAdapter {

    public QuestionManagerViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new ParticipantListFragment();
            case 1:
                return new QuestionListFragment();
            case 2:
                return new ParticipantAnalyticsListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String tabTitle = "";
        switch(position)
        {
            case 0:
                tabTitle = "Danh sách";
                break;
            case 1:
                tabTitle = "Trắc nghiệm";
                break;
            case 2:
                tabTitle = "Câu trả lời";
                break;
        }
        return tabTitle;
    }
}
