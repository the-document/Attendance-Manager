package com.example.nguyenhongphuc98.checkmein.Data.db.model;

public class Account {
    private String mail;
    private String password;
    private int mssv;

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public int getMssv() {
        return mssv;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMssv(int mssv) {
        this.mssv = mssv;
    }
}
