package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Question {
    private String id = null;
    private String content;
    private ArrayList<Answer> mAnswers;
    private String questionType;
    private String event;
    private String public_time;

    public Question()
    {

    }

    public Question(String content, ArrayList<Answer>answers)
    {
        this.content = content;
        this.mAnswers = new ArrayList<Answer>();
        this.mAnswers = answers;
    }

    public Question(String content, Answer ...answers) {
        this.content = content;
        this.mAnswers = new ArrayList<Answer>();
        for (Answer answer : answers)
            mAnswers.add(answer);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Answer> getmAnswers() {
        return mAnswers;
    }

    public void setmAnswers(ArrayList<Answer> mAnswers) {
        this.mAnswers = mAnswers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getPublic_time() {
        return public_time;
    }

    public void setPublic_time(String public_time) {
        this.public_time = public_time;
    }
}
