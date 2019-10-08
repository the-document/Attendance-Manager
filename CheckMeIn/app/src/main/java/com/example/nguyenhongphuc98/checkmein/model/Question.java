package com.example.nguyenhongphuc98.checkmein.model;

import java.util.ArrayList;

public class Question {
    private String mQuestion;
    private ArrayList<String> mAnswers;

    public Question(String mQuestion, ArrayList<String>answers)
    {
        this.mQuestion = mQuestion;
        this.mAnswers = new ArrayList<String>();
        this.mAnswers = answers;
    }

    public Question(String mQuestion, String ...answers) {
        this.mQuestion = mQuestion;
        this.mAnswers = new ArrayList<String>();
        for (String answer : answers)
            mAnswers.add(answer);
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
