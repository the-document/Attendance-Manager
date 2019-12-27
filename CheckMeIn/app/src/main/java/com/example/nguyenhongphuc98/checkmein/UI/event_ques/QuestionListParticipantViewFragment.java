package com.example.nguyenhongphuc98.checkmein.UI.event_ques;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.adapter.QuestionListCustomAdapter;

import java.util.ArrayList;

public class QuestionListParticipantViewFragment extends Fragment {
    ListView lv_question_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_question_participant_view, container, false);
        lv_question_list = (ListView)view.findViewById(R.id.lv_question_list_participant_view);

        ArrayList<Question> dsTest = new ArrayList<>();

        QuestionListCustomAdapter qaCustomAdapter = new QuestionListCustomAdapter(getActivity(), R.layout.custom_question_row_layout, dsTest);

        lv_question_list.setAdapter(qaCustomAdapter);

        return view;
    }
}
