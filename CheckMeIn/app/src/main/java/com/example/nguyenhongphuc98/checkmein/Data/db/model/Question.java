package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import java.util.ArrayList;

public class Question {
    private int id;
    private String mQuestion;
    private ArrayList<String> mAnswers;
    private String questionType;
    private int idEvent;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
