package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import java.util.List;

public class ParticipantAnswerByQuestion {
    private String questionKey;
    private List<String> answersKey;

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

    public List<String> getAnswersKey() {
        return answersKey;
    }

    public void setAnswersKey(List<String> answersKey) {
        this.answersKey = answersKey;
    }
}
