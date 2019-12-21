package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Answer {
    private String question;
    private String key;
    private boolean is_correct;
    private String content;

    public Answer() {
    }

    public Answer(String question, String key, boolean is_correct, String content) {
        this.question = question;
        this.key = key;
        this.is_correct = is_correct;
        this.content = content;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isIs_correct() {
        return is_correct;
    }

    public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
