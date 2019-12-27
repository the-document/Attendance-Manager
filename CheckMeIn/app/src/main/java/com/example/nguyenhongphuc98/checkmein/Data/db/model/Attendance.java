package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import androidx.annotation.NonNull;

public class Attendance {
    String event_key;
    String user_key;
    String user_name;

    public String getEvent_key() {
        return event_key;
    }

    public String getUser_key() {
        return user_key;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setEvent_key(String event_key) {
        this.event_key = event_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
