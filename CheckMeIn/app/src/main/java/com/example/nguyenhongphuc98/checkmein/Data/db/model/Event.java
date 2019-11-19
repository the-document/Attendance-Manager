package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private int id;
    private int organizeId;
    private String eventCode;
    private String eventName;
    private ArrayList<Integer> idEventQuestions;
    private ArrayList<Person> participantList;
    private ArrayList<Person> registrationList;
    private LocalDateTime time;

    public int getId() {
        return id;
    }

    public int getOrganizeId() {
        return organizeId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public ArrayList getIdEventQuestions() {
        return idEventQuestions;
    }

    public ArrayList getParticipantList() {
        return participantList;
    }

    public ArrayList getRegistrationList() {
        return registrationList;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrganizeId(int organizeId) {
        this.organizeId = organizeId;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setIdEventQuestions(ArrayList idEventQuestions) {
        this.idEventQuestions = idEventQuestions;
    }

    public void setParticipantList(ArrayList participantList) {
        this.participantList = participantList;
    }

    public void setRegistrationList(ArrayList registrationList) {
        this.registrationList = registrationList;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
