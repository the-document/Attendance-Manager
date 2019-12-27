package com.example.nguyenhongphuc98.checkmein.UI.event_ques.listing_question;

import com.example.nguyenhongphuc98.checkmein.adapter.QuestionListCustomAdapter;
import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.event_ques.new_question_dialog.NewQuestionDialogContract;
import com.example.nguyenhongphuc98.checkmein.UI.event_ques.new_question_dialog.NewQuestionDialogFragment;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.adapter.QuestionListCustomAdapter;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;

public class QuestionListPresenter implements QuestionListContract.QuestionListPresenter {

    QuestionListFragment view;
    ArrayList<Question> questionsList;

    @Override
    public void setView(QuestionListFragment view) {
        this.view = view;
    }

    @Override
    public void loadQuestions() {
        //Đây chỉ là thêm vào để test presenter mà thôi.
        questionsList = new ArrayList<>();
        QuestionListCustomAdapter qaCustomAdapter = new QuestionListCustomAdapter(view.getContext(), R.layout.custom_question_row_layout, questionsList);
        view.setQuestionListAdapter(qaCustomAdapter);
        DataManager.Instance().LoadQuestions(qaCustomAdapter, questionsList, DataCenter.EventID);
    }

    public void questionClicked(int position){
        if (questionsList == null)
            return;
        Question question = questionsList.get(position);
        FragmentManager fm = view.getChildFragmentManager();
        NewQuestionDialogFragment dlFragment = NewQuestionDialogFragment.newInstance();

        dlFragment.setQuestion(question);
        dlFragment.show(fm, "fragment_edit_question_and_answers");
    }
}
