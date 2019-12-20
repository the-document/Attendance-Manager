package com.example.nguyenhongphuc98.checkmein.UI.event_ques.listing_question;

import com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.R;

import java.util.ArrayList;

public class QuestionListPresenter implements QuestionListContract.QuestionListPresenter {

    QuestionListFragment view;
    String eventID = "event1";

    @Override
    public void setView(QuestionListFragment view) {
        this.view = view;
    }

    @Override
    public void loadQuestions() {
        //Đây chỉ là thêm vào để test presenter mà thôi.
        ArrayList<Question> questionsList = new ArrayList<>();

        QuestionListCustomAdapter qaCustomAdapter = new QuestionListCustomAdapter(view.getContext(), R.layout.custom_question_row_layout, questionsList);
        view.setQuestionListAdapter(qaCustomAdapter);
        DataManager.Instance().LoadQuestions(qaCustomAdapter, questionsList, eventID);
    }
}
