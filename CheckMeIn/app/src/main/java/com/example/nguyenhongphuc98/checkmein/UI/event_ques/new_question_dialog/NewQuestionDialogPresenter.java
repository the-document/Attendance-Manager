package com.example.nguyenhongphuc98.checkmein.UI.event_ques.new_question_dialog;

import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.CheckMeIn;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Answer;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

import java.util.ArrayList;

public class NewQuestionDialogPresenter implements NewQuestionDialogContract.NewQuestionDialogPresenter {
    private NewQuestionDialogFragment view;

    @Override
    public void setView(NewQuestionDialogFragment view) {
        this.view = view;
    }

    @Override
    public void finishAddingQuestion() {

        ArrayList<AnswerView> answerViews = view.getAnswerViewArrayList();
        ArrayList<Answer> answers = new ArrayList<>();

        String sQuestion = view.getmEdtQuestion().getText().toString();
        Question question = view.getQuestion();

        if (question == null)
            question = new Question();

        //Chỉnh lại nội dung câu hỏi từ input của người dùng nè.
        question.setContent(sQuestion);

        //Chỉnh lại luôn danh sách các câu trả lời nè.
        for (int i=0;i<answerViews.size();++i){
            AnswerView answerView = answerViews.get(i);

            int intAnswerKey = 65 + i;
            char charAnswerKey = (char)intAnswerKey;
            String stringAnswerKey = String.valueOf(charAnswerKey);

            Answer answer = new Answer(question.getId(), stringAnswerKey,
                    answerView.checkBoxCorrectAnswer.isChecked(), answerView.getAnswerText());
            answers.add(answer);
        }

        question.setmAnswers(answers);

        DataManager.Instance().SaveQuestion(question);

        Toast.makeText(CheckMeIn.getAppContext(), "Thêm câu trả lời", Toast.LENGTH_LONG).show();
        view.dismiss();
    }
}
