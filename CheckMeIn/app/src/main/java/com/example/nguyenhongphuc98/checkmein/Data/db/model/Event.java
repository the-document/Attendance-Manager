package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
   String avatar;
   String begin_time;
   String end_time;
   String event_code;
   String location;
   String organ;
   String event_id;
   String event_day;
   String event_name;

    public Event(){}

    public Event(String avatar, String begin_time, String end_time,
                 String event_code, String location, String organ,
                 String id,String evd,String evn) {
        this.avatar = avatar;
        this.begin_time = begin_time;
        this.end_time = end_time;
        this.event_code = event_code;
        this.location = location;
        this.organ = organ;
        this.event_id=id;
        this.event_day =evd;
        this.event_name=evn;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getEvent_code() {
        return event_code;
    }

    public void setEvent_code(String event_code) {
        this.event_code = event_code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_day() {
        return event_day;
    }

    public void setEvent_day(String event_day) {
        this.event_day = event_day;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }
}
