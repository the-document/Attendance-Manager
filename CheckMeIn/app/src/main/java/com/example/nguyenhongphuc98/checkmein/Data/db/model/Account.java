package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class Account {
    private String username;
    private String password;
    private String create_date;
    private String person;
    private DataManager dataManager;

    public Account() {
        dataManager = new DataManager();
    }

    public Account(String create_date, String password, String person, String username) {
        this.username = username;
        this.password = password;
        this.person = person;
        this.create_date = create_date;
    }


    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPerson() {
        return person;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void writeNewAccount(String userId, String username, String person, String password, String create_date) {
        Account account = new Account(create_date, password, person, username);

        dataManager.mDatabase.child("Account").child(userId).setValue(account);
    }
}
