package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Person {
    private int mssv;
    private int phone;
    private String avatar;
    private String displayName;
    private String userClass;
    private DataManager dataManager;

    private static final String TAG = "PersonInfo";

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

    public void checkNewPerson(int mssv) {
        dataManager.mDatabase = dataManager.database.getReference("Person");
        dataManager.mDatabase.orderByChild("mssv").equalTo(mssv).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    return;
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void writeNewPerson(int mssv, int phone, String avatar, String displayName, String userClass) {
        Person person = new Person(mssv, phone, avatar, displayName, userClass);
        String key = dataManager.mDatabase.child("Person").push().getKey();

        dataManager.database.getReference("Person").orderByChild("mssv")
                .equalTo(mssv).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.w(TAG, "User already registed.");
                    return;
                } else {
                    Log.d(TAG, "User regists successfully.");
                    dataManager.mDatabase.child("Person").child(key).setValue(person);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
