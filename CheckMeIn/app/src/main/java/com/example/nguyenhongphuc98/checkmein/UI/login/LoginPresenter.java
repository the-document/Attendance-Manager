package com.example.nguyenhongphuc98.checkmein.UI.login;

import android.text.TextUtils;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class LoginPresenter {
    private Account account;
    private ILoginView view;
    private DataManager dataManager;

    public LoginPresenter(ILoginView view) {
        this.account = new Account();
        this.view = view;
        dataManager = new DataManager();
    }


    public String getPassword() {
        return this.account.getPassword();
    }

    public void LoginProcess(String email, String password) {
        dataManager.ProcessLogin(email, password);
    }

    public boolean ValidateForm(TextView edtUsername, TextView edtPassword) {
        boolean valid = true;

        String userName = edtUsername.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            edtUsername.setError("Required.");
            valid = false;
        } else {
            edtUsername.setError(null);
        }

        String password = edtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Required.");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        return valid;
    }

    public boolean CheckLoginStatus() {
        return dataManager.checkLoginStatus();
    }

    public boolean CheckEmailVerify() {
        return dataManager.checkEmailVerify();
    }
}