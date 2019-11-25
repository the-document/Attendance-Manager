package com.example.nguyenhongphuc98.checkmein.UI.event_ans;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.adapter.ParticipantAnswerDetailsCustomAdapter;

public class ParticipantAnalyticsListFragment extends Fragment implements ParticipantAnalyticsListFragmentContract.ParticipantAnalyticsListFragmentView{

    ListView lv_participant_analytics_list;
    ParticipantAnalyticsListFragmentContract.ParticipantAnalyticsListFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_participant_analytics, container, false);
        lv_participant_analytics_list = (ListView)view.findViewById(R.id.lv_fragment_list_of_participant_analytics);

        //Cài đặt presenter cho view.
        presenter = new ParticipantAnalyticsListFragmentPresenter();
        presenter.setView(this);

        //Load tất cả thống kê.
        presenter.loadParticipantAnalytics();

        return view;
    }

    @Override
    public void setParticipantAnalyticsAdapter(ParticipantAnswerDetailsCustomAdapter adapter) {
        lv_participant_analytics_list.setAdapter(adapter);
    }
}
