package com.example.nguyenhongphuc98.checkmein.Data.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DataManager {
    Account account;
    FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    //public static final String EXTRA_USERNAME = ".LOGIN.USERNAME";
    //public static final String EXTRA_PASSWORD = ".LOGIN.PASSWORD";
    //public static final int REGISTER_SUCCESS = 1;
    //public static final int REGISTER_NOT = 0;
    //fire base implement here
    public DataManager() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void getLoginInfo(String email, String password) {
        account.setMail(email);
        account.setPassword(password);
    }

    public boolean checkLoginStatus()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            return true;
        } else {
            // No user is signed in
            return false;
        }
    }

    public void ProcessLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }
}
