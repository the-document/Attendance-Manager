package com.example.nguyenhongphuc98.checkmein;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.model.Question;

import java.util.ArrayList;

public class fragment_list_of_question_participant_view extends Fragment {
    ListView lv_question_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_question_participant_view, container, false);
        lv_question_list = (ListView)view.findViewById(R.id.lv_question_list_participant_view);

        ArrayList<Question> dsTest = new ArrayList<>();

        Question question1 = new Question("Câu hỏi số 1", "Câu trả lời A", "Câu trả lời B", "Câu trả lời C", "Câu trả lời D");
        Question question2 = new Question("Câu hỏi số 2", "Câu trả lời A", "Câu trả lời B", "Câu trả lời C", "Câu trả lời D");

        dsTest.add(question1);
        dsTest.add(question2);

        com.example.nguyenhongphuc98.checkmein.adapter.QuestionListCustomAdapter qaCustomAdapter = new com.example.nguyenhongphuc98.checkmein.adapter.QuestionListCustomAdapter(getActivity(), R.layout.custom_question_row_layout, dsTest);

        lv_question_list.setAdapter(qaCustomAdapter);

        return view;
    }
}