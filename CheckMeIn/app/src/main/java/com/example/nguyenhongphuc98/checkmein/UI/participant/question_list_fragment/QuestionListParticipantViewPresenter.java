package com.example.nguyenhongphuc98.checkmein.UI.participant.question_list_fragment;

import android.os.SystemClock;
import android.view.View;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.ParticipantAnswerDetailsDAL;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.R;

import java.util.ArrayList;

public class QuestionListParticipantViewPresenter implements QuestionListParticipantViewContract.QuestionListParticipantViewPresenter {
    QuestionListParticipantViewFragment view;
    ArrayList<Question> questionsList;
    long startTime;
    long endTime;

    public QuestionListParticipantViewPresenter(QuestionListParticipantViewFragment fragmentView){
        this.view = fragmentView;
        startTime = SystemClock.elapsedRealtime();
    }

    @Override
    public void LoadQuestionList() {
        questionsList = new ArrayList<>();
        com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter qaCustomAdapter = new com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter(view.getActivity(), R.layout.custom_question_row_layout, questionsList, true);
        view.lv_question_list.setAdapter(qaCustomAdapter);
        DataManager.Instance().LoadQuestionWithoutAnswerHighlight(qaCustomAdapter, questionsList, DataCenter.EventID);
        view.acbFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qaCustomAdapter.finishAnswering();
                ParticipantAnswerDetailsDAL userScore = calculateUserScore();
                DataManager.Instance().SaveUserAnswerResult(userScore, DataCenter.UserID,DataCenter.EventID);
            }
        });
    }

    private ParticipantAnswerDetailsDAL calculateUserScore(){
        ParticipantAnswerDetailsDAL dal = new ParticipantAnswerDetailsDAL();
        int numCorrect = 0;
        for (Question question : questionsList){
            if (question.isQuestionAnsweredCorrectly())
                ++numCorrect;
        }

        endTime = SystemClock.elapsedRealtime();
        long elapsedTime = endTime - startTime;

        dal.setNum_correct(numCorrect);
        dal.setTime_elapsed(elapsedTime);
        dal.setTotal_question(questionsList.size());
        dal.setUser_name(DataCenter.UserDisplayName);
        return dal;
    }
}
