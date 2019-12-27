package com.example.nguyenhongphuc98.checkmein.UI.participant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.participant.question_list_fragment.QuestionListParticipantViewFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ParticipantViewFragment extends Fragment {

    TextView txtViewEventName;
    TextView txtViewEventCode;
    FrameLayout frameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_question_manager_participant_view, container, false);
        txtViewEventName = (TextView)view.findViewById(R.id.activity_question_manager_participant_view_txtView_event_name);
        txtViewEventCode = (TextView)view.findViewById(R.id.activity_question_manager_participant_view_txtView_event_code);
        frameLayout = (FrameLayout)view.findViewById(R.id.activity_question_manager_participant_view_frame_container);

        txtViewEventName.setText(DataCenter.Event.getEvent_name());
        txtViewEventCode.setText(DataCenter.Event.getEvent_code());
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.activity_question_manager_participant_view_frame_container, new QuestionListParticipantViewFragment());
        ft.commit();

        return view;
    }
}
