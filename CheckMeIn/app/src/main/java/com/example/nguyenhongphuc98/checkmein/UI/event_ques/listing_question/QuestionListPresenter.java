package com.example.nguyenhongphuc98.checkmein.UI.event_ques.listing_question;

import com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.R;

import java.util.ArrayList;

public class QuestionListPresenter implements QuestionListContract.QuestionListPresenter {

    QuestionListFragment view;

    @Override
    public void setView(QuestionListFragment view) {
        this.view = view;
    }

    @Override
    public void loadQuestions() {
        //Đây chỉ là thêm vào để test presenter mà thôi.
        ArrayList<Question> dsTest = new ArrayList<>();

        Question question1 = new Question("Câu hỏi số 1", "Câu trả lời A", "Câu trả lời B", "Câu trả lời C", "Câu trả lời D", "Câu trả lời E");
        Question question2 = new Question("Câu hỏi số 2", "Câu trả lời A", "Câu trả lời B", "Câu trả lời C", "Câu trả lời D");

        dsTest.add(question1);
        dsTest.add(question2);
        QuestionListCustomAdapter qaCustomAdapter = new QuestionListCustomAdapter(view.getContext(), R.layout.custom_question_row_layout, dsTest);
        view.setQuestionListAdapter(qaCustomAdapter);
    }
}
