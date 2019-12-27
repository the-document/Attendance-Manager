package com.example.nguyenhongphuc98.checkmein.UI.participant.question_list_fragment;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.R;

import java.util.ArrayList;

public class QuestionListParticipantViewPresenter implements QuestionListParticipantViewContract.QuestionListParticipantViewPresenter {
    QuestionListParticipantViewFragment view;

    public QuestionListParticipantViewPresenter(QuestionListParticipantViewFragment fragmentView){
        this.view = fragmentView;
    }

    @Override
    public void LoadQuestionList() {
        ArrayList<Question> questionsList = new ArrayList<>();
        com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter qaCustomAdapter = new com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter(view.getActivity(), R.layout.custom_question_row_layout, questionsList);
        view.lv_question_list.setAdapter(qaCustomAdapter);
        DataManager.Instance().LoadQuestionWithoutAnswerHighlight(qaCustomAdapter, questionsList, DataCenter.EventID);
    }
}
