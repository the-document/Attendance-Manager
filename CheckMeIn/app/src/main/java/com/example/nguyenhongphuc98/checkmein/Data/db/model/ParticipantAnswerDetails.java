package com.example.nguyenhongphuc98.checkmein.Data.db.model;

public class ParticipantAnswerDetails {
    private String fullName;
    private int ranking;
    private int totalParticipants;
    private int numOfCorrectAnswer;
    private int totalAnswer;
    private long timeElapsedInSeconds;

    private String sRanking;
    private String sRatioOfCorrectAnswers;
    private String sTimeElapsed;

    public ParticipantAnswerDetails(String fullName, int ranking, int totalParticipants, int numOfCorrectAnswer, int totalAnswer, long timeElapsedInSeconds) {
        this.fullName = fullName;
        this.ranking = ranking;
        this.totalParticipants = totalParticipants;
        this.numOfCorrectAnswer = numOfCorrectAnswer;
        this.totalAnswer = totalAnswer;
        this.timeElapsedInSeconds = timeElapsedInSeconds;
        convertEverythingToString();
    }
    public String getFullName() {
        return fullName;
    }

    public int getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(int totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getNumOfCorrectAnswer() {
        return numOfCorrectAnswer;
    }

    public void setNumOfCorrectAnswer(int numOfCorrectAnswer) {
        this.numOfCorrectAnswer = numOfCorrectAnswer;
    }

    public int getTotalAnswer() {
        return totalAnswer;
    }

    public void setTotalAnswer(int totalAnswer) {
        this.totalAnswer = totalAnswer;
    }

    public long getTimeElapsed() {
        return timeElapsedInSeconds;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsedInSeconds = timeElapsed;
    }

    public String getsRanking() {
        return sRanking;
    }

    public String getsRatioOfCorrectAnswers() {
        return sRatioOfCorrectAnswers;
    }

    public String getsTimeElapsed() {
        return sTimeElapsed;
    }

    private void convertEverythingToString()
    {
        sRanking = String.format("#%d/%d", ranking, totalParticipants);
        sRatioOfCorrectAnswers = String.format("%d/%d câu trả lời đúng", numOfCorrectAnswer, totalAnswer);
        sTimeElapsed = "Hoàn thành trong " + getBeautyTimeFromSeconds(timeElapsedInSeconds);
    }

    private String getBeautyTimeFromSeconds(long seconds){
        long minutes;
        long hours;
        long days;
        String result = "";

        minutes = seconds / 60;
        seconds = seconds % 60;

        hours = minutes / 60;
        minutes = minutes % 60;

        days = hours / 60;
        hours = hours % 60;

        if (days > 0)
            result += String.format("%d ngày ", days);
        if (hours > 0)
            result += String.format("%d giờ ", hours);
        if (minutes > 0)
            result += String.format("%d phút ", minutes);
        if (seconds > 0)
            result += String.format("%d giây", seconds);

        return result;
    }
}
