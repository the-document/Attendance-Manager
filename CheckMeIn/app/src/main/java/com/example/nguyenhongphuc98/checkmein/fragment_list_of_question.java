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

import com.example.nguyenhongphuc98.checkmein.adapter.QuestionListCustomAdapter;
import com.example.nguyenhongphuc98.checkmein.model.Question;

import java.util.ArrayList;

public class fragment_list_of_question extends Fragment{

    ListView lv_question_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Test thử hiển thị danh sách câu hỏi.
        View view = inflater.inflate(R.layout.fragment_list_of_question, container, false);
        lv_question_list = (ListView)view.findViewById(R.id.lv_question_list);

        ArrayList<Question> dsTest = new ArrayList<>();

        Question question1 = new Question("Câu hỏi số 1", "Câu trả lời A", "Câu trả lời B", "Câu trả lời C", "Câu trả lời D");
        Question question2 = new Question("Câu hỏi số 2", "Câu trả lời A", "Câu trả lời B", "Câu trả lời C", "Câu trả lời D");

        dsTest.add(question1);
        dsTest.add(question2);

        QuestionListCustomAdapter qaCustomAdapter = new QuestionListCustomAdapter(getActivity(), R.layout.custom_question_row_layout, dsTest);

        lv_question_list.setAdapter(qaCustomAdapter);

        return view;
    }
}
