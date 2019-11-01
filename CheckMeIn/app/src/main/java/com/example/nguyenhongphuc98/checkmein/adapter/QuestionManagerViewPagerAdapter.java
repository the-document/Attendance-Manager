package com.example.nguyenhongphuc98.checkmein.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nguyenhongphuc98.checkmein.fragment_list_of_participant;
import com.example.nguyenhongphuc98.checkmein.fragment_list_of_participant_analytics;
import com.example.nguyenhongphuc98.checkmein.fragment_list_of_question;

public class QuestionManagerViewPagerAdapter extends FragmentPagerAdapter {

    public QuestionManagerViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new fragment_list_of_participant();
            case 1:
                return new fragment_list_of_question();
            case 2:
                return new fragment_list_of_participant_analytics();
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
