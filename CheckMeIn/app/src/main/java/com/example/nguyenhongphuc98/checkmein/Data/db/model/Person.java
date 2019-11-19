package com.example.nguyenhongphuc98.checkmein.Data.db.model;

public class Person {
    private int mssv;
    private String filePath;
    private String displayName;
    private String userClass;

    public Person() {}

    public int getMssv() {
        return mssv;
    }

    public String getFilePath() {
        return filePath;
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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }
}
