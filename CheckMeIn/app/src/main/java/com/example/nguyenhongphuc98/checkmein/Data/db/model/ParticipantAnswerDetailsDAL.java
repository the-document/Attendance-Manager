package com.example.nguyenhongphuc98.checkmein.Data.db.model;

public class ParticipantAnswerDetailsDAL implements Comparable<ParticipantAnswerDetailsDAL>{

    int num_correct;
    int time_elapsed;
    int total_question;
    String user_name;

    public ParticipantAnswerDetailsDAL() {
    }

    public ParticipantAnswerDetailsDAL(int num_correct, int time_elapsed, int total_question, String user_name) {
        this.num_correct = num_correct;
        this.time_elapsed = time_elapsed;
        this.total_question = total_question;
        this.user_name = user_name;
    }

    public int getNum_correct() {
        return num_correct;
    }

    public void setNum_correct(int num_correct) {
        this.num_correct = num_correct;
    }

    public int getTime_elapsed() {
        return time_elapsed;
    }

    public void setTime_elapsed(int time_elapsed) {
        this.time_elapsed = time_elapsed;
    }

    public int getTotal_question() {
        return total_question;
    }

    public void setTotal_question(int total_question) {
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
