package com.example.nguyenhongphuc98.checkmein.UI.event_ques.new_question_dialog;

import android.widget.Toast;

public class NewQuestionDialogPresenter implements NewQuestionDialogContract.NewQuestionDialogPresenter {
    private NewQuestionDialogFragment view;

    @Override
    public void setView(NewQuestionDialogFragment view) {
        this.view = view;
    }

    @Override
    public void finishAddingQuestion() {
        Toast.makeText(view.getContext(), "Thêm câu trả lời", Toast.LENGTH_LONG).show();
    }
}
