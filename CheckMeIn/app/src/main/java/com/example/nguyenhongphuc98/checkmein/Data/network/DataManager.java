package com.example.nguyenhongphuc98.checkmein.Data.network;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicBoolean;

public class DataManager {
    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public final FirebaseDatabase database;

    private static final String TAG = "EmailPassword";

    //fire base implement here
    public DataManager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public boolean checkLoginStatus() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            return true;
        } else {
            // No user is signed in
            return false;
        }
    }

    public boolean checkEmailVerify() {
        return mAuth.getCurrentUser().isEmailVerified();
    }

    public void ProcessLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                    }
                });
    }

    public boolean registerProcess(String userName, String passWord) {

        Log.d(TAG, "createAccount:" + userName);

        // [START create_user_with_userName]
        mAuth.createUserWithEmailAndPassword(userName, passWord)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Log.d(TAG, "createUserWithEmail:success");
                        // [START send_email_verification]
                        mAuth.getCurrentUser().sendEmailVerification()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d(TAG, "Verificate mail sent.");
                                    }
                                    else Log.w(TAG, "Send verificate mail failed.");
                                });
                        // [END send_email_verification]
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                });
        // [END create_user_with_email]

        if (mAuth.getCurrentUser() != null) return true;
        else return false;
    }

    public Task sendMailResetPassword(String emailAddress) {
        return mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Reset email sent.");
                    }
                    else Log.w(TAG, "Send mail rest failed.");
                });
    }
}
