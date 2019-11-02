package com.example.nguyenhongphuc98.checkmein;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

public class NewQuestionDialogFragment extends DialogFragment {

    private EditText mEdtQuestion;
    private EditText mEdtAnswer1;
    private Button mButtonAddQuestion;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Ánh xạ.
        AssignAllControl(view);
    }

    private void AssignAllControl(View view)
    {
        mEdtQuestion = view.findViewById(R.id.fragment_add_question_and_answers_edt_question);
        mEdtAnswer1 = view.findViewById(R.id.fragment_add_question_and_answers_answer_1);
        mButtonAddQuestion = view.findViewById(R.id.fragment_add_question_and_answers_btn_add_question);
        mButtonFinish = view.findViewById(R.id.fragment_add_question_and_answers_btn_accept);
    }
}
