package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class Person {
    private int mssv;
    private int phone;
    private String avatar;
    private String displayName;
    private String userClass;
    private DataManager dataManager;

    public Person(int mssv, int phone, String avatar, String displayName, String userClass) {
        this.avatar = avatar;
        this.userClass = userClass;
        this.displayName = displayName;
        this.mssv = mssv;
        this.phone = phone;
    }

    public Person() {
        dataManager = new DataManager();
    }

    public int getMssv() {
        return mssv;
    }

    public int getPhone() {
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


    public void setMssv(int mssv) {
        this.mssv = mssv;
    }

    public void setPhone(int phone) {
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


    public void writeNewPerson(int mssv, int phone, String avatar, String displayName, String userClass) {
        Person person = new Person(mssv, phone, avatar, displayName, userClass);
        String key = dataManager.mDatabase.child("Person").push().getKey();

        dataManager.mDatabase.child("Person").child(key).setValue(person);
    }
}
