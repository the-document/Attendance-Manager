package com.example.nguyenhongphuc98.checkmein.UI.event_ques.new_question_dialog;

public interface NewQuestionDialogContract {
    interface NewQuestionDialogPresenter{
        void setView(NewQuestionDialogFragment view);
        void finishAddingQuestion();
    }
    interface NewQuestionDialogView{

    }
}
