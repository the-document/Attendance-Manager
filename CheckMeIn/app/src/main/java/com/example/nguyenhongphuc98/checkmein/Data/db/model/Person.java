package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Person {
    private String mssv;
    private String phone;
    private String avatar;
    private String displayName;
    private String userClass;
    private DataManager dataManager;

    private static final String TAG = "PersonInfo";

    public Person(String mssv, String phone, String avatar, String displayName, String userClass) {
        this.avatar = avatar;
        this.userClass = userClass;
        this.displayName = displayName;
        this.mssv = mssv;
        this.phone = phone;
    }

    public Person() {
        dataManager = new DataManager();
    }

    public String getMssv() {
        return mssv;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserClass() {
        return userClass;
    }


    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

}