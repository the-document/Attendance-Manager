package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ParticipantAnswerDetailsDAL implements Comparable<ParticipantAnswerDetailsDAL>{

    long num_correct;
    long time_elapsed;
    long total_question;
    String user_name;

    @Exclude
    String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ParticipantAnswerDetailsDAL() {
    }

    public ParticipantAnswerDetailsDAL(long num_correct, long time_elapsed, long total_question, String user_name) {
        this.num_correct = num_correct;
        this.time_elapsed = time_elapsed;
        this.total_question = total_question;
        this.user_name = user_name;
    }

    public long getNum_correct() {
        return num_correct;
    }

    public void setNum_correct(long num_correct) {
        this.num_correct = num_correct;
    }

    public long getTime_elapsed() {
        return time_elapsed;
    }

    public void setTime_elapsed(long time_elapsed) {
        this.time_elapsed = time_elapsed;
    }

    public long getTotal_question() {
        return total_question;
    }

    public void setTotal_question(long total_question) {
        this.total_question = total_question;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public int compareTo(ParticipantAnswerDetailsDAL o) {
        if(this.getNum_correct() == o.getNum_correct())
        {
            return this.getTime_elapsed()>o.getTime_elapsed()?1:-1;
        }

        return this.getNum_correct() < o.getNum_correct()?1:-1;
    }
}
