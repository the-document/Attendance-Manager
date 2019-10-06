package com.example.nguyenhongphuc98.checkmein.model;

import java.util.ArrayList;

public class Question {
    private String mQuestion;
    private ArrayList<String> mAnswers;

    public Question(String mQuestion, ArrayList<String> mAnswers) {
        this.mQuestion = mQuestion;
        this.mAnswers = mAnswers;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public ArrayList<String> getmAnswers() {
        return mAnswers;
    }

    public void setmAnswers(ArrayList<String> mAnswers) {
        this.mAnswers = mAnswers;
    }
}
