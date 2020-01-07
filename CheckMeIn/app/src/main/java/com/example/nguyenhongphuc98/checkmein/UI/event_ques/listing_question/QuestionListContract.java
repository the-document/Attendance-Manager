package com.example.nguyenhongphuc98.checkmein.UI.event_ques.listing_question;

import com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter;

public interface QuestionListContract {
    interface QuestionListView {
        void setQuestionListAdapter(QuestionListCustomAdapter adapter);
    }
    interface QuestionListPresenter{
        void setView(QuestionListFragment view);
        void loadQuestions();
        void questionClicked(int position);
        void onAddNewQuestionClicked();
    }
}
