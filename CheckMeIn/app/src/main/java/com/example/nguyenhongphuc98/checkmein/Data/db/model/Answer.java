package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Answer {
    private String question;
    private String key;
    private boolean is_correct;
    private String content;

    @Exclude
    private boolean is_choosen;
    @Exclude
    private boolean is_show_answer = true;
    @Exclude
    private boolean is_answer_selection_locked;
    @Exclude
    private Question questionObject;

    public Answer() {

    }

    public Answer(String question, String key, boolean is_correct, String content) {
        this.question = question;
        this.key = key;
        this.is_correct = is_correct;
        this.content = content;
    }

    public Question getQuestionObject() {
        return questionObject;
    }

    public void setQuestionObject(Question questionObject) {
        this.questionObject = questionObject;
    }

    public boolean isIs_answer_selection_locked() {
        return is_answer_selection_locked;
    }

    public void setIs_answer_selection_locked(boolean is_answer_selection_locked) {
        this.is_answer_selection_locked = is_answer_selection_locked;
    }

    public boolean isIs_show_answer() {
        return is_show_answer;
    }

    public void setIs_show_answer(boolean is_show_answer) {
        this.is_show_answer = is_show_answer;
    }

    public boolean isIs_choosen() {
        return is_choosen;
    }

    public void setIs_choosen(boolean is_choosen) {
        if (!is_answer_selection_locked)
            this.is_choosen = is_choosen;
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
