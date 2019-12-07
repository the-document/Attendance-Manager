package com.example.nguyenhongphuc98.checkmein.UI.sign_up;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Person;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterPresenter {
    IRegisterView view;
    DataManager dataManager;
    Account account;
    Person person;

    public RegisterPresenter(IRegisterView view){
        this.view = view;
        account = new Account();
        person =  new Person();
        dataManager = new DataManager();
    }

    public FirebaseUser getCurrentUser(){
        return dataManager.mAuth.getCurrentUser();
    }

    public boolean SignUpProcess(String email, String password){
        return dataManager.registerProcess(email, password);
    }

    public void writeNewAccount(String userId, String username, int mssv, String password, String create_date) {
        dataManager.mDatabase = dataManager.database.getReference("Person");
        dataManager.mDatabase.orderByChild("mssv").equalTo(mssv).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Person person = dataSnapshot.getValue(Person.class);
                //System.out.println(dataSnapshot.getKey() + " was " + dinosaur.height + " meters tall.");
                account.writeNewAccount(userId, username, dataSnapshot.getKey(), password, create_date);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void writeNewPerson(int mssv) {
        person.writeNewPerson(mssv, 0, "", "New User", "");
    }
}
