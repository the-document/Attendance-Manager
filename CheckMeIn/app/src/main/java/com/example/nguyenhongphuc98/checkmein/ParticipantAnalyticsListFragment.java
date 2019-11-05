package com.example.nguyenhongphuc98.checkmein;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.adapter.ParticipantAnswerDetailsCustomAdapter;
import com.example.nguyenhongphuc98.checkmein.model.ParticipantAnswerDetails;

import java.util.ArrayList;

public class ParticipantAnalyticsListFragment extends Fragment {

    ListView lv_participant_analytics_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_participant_analytics, container, false);
        lv_participant_analytics_list = (ListView)view.findViewById(R.id.lv_fragment_list_of_participant_analytics);

        ArrayList<ParticipantAnswerDetails> dsTest = new ArrayList<>();

        ParticipantAnswerDetails desc1 = new ParticipantAnswerDetails("Lưu Biêu Nghị", 1, 100, 100, 100, 500);
        ParticipantAnswerDetails desc2 = new ParticipantAnswerDetails("Nguyễn Hồng Phúc", 1, 50, 50, 50, 152035);
        ParticipantAnswerDetails desc3 = new ParticipantAnswerDetails("Nguyễn Hồng Ngọc", 1, 50, 50, 50, 152035);

        dsTest.add(desc1);
        dsTest.add(desc2);
        dsTest.add(desc3);

        ParticipantAnswerDetailsCustomAdapter padCustomAdapter = new ParticipantAnswerDetailsCustomAdapter(getActivity(), R.layout.fragment_list_of_participant_analytics, dsTest);

        lv_participant_analytics_list.setAdapter(padCustomAdapter);

        return view;
    }
}
