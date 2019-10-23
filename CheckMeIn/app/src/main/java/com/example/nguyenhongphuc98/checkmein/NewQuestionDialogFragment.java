package com.example.nguyenhongphuc98.checkmein;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class NewQuestionDialogFragment extends DialogFragment {

    private EditText mEdtQuestion;
    private EditText mEdtAnswer1;
    private ArrayList<EditText> additionalAnswers = new ArrayList<EditText>();

    private Button mButtonAddAnswer;
    private Button mButtonFinish;

    public NewQuestionDialogFragment()
    {

    }

    public static NewQuestionDialogFragment newInstance()
    {
        NewQuestionDialogFragment fragment = new NewQuestionDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_question_and_answers, container);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Ánh xạ.
        AssignAllControl(view);

        //Thêm listener bắt sự kiện khi người dùng muốn thêm câu hỏi.
        mButtonAddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newAnswer = new EditText(getContext());

            }
        });
    }

    private void AssignAllControl(View view)
    {
        mEdtQuestion = view.findViewById(R.id.fragment_add_question_and_answers_edt_question);
        mEdtAnswer1 = view.findViewById(R.id.fragment_add_question_and_answers_answer_1);
        mButtonAddAnswer = view.findViewById(R.id.fragment_add_question_and_answers_btn_add_answer);
        mButtonFinish = view.findViewById(R.id.fragment_add_question_and_answers_btn_accept);
    }
}
