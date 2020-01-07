package com.example.nguyenhongphuc98.checkmein.UI.event_ques;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.R;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.adapter.QuestionManagerViewPagerAdapter;

public class QuestionManagementFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_question_manager, container, false);

        ViewPager viewPager = view.findViewById(R.id.pager_question_manager);
        viewPager.setAdapter(new QuestionManagerViewPagerAdapter(getChildFragmentManager()));

        TabLayout tabLayout = view.findViewById(R.id.tab_layout_question_manager);
        tabLayout.setupWithViewPager(viewPager);

        TextView txtViewEventName = view.findViewById(R.id.activity_question_manager_participant_view_txtView_event_name);
        TextView txtViewEventCode = view.findViewById(R.id.activity_question_manager_participant_view_txtView_event_code);

        txtViewEventName.setText(DataCenter.Event.getEvent_name());
        txtViewEventCode.setText(DataCenter.Event.getEvent_code());

        return view;
    }
}