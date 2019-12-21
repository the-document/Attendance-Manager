package com.example.nguyenhongphuc98.checkmein.UI.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class LoginPresenter implements LoginCallback{
    private Account account;
    private LoginActivity view;
    private DataManager dataManager;

    public LoginPresenter(LoginActivity view) {
        this.account = new Account();
        this.view = view;
        dataManager = new DataManager();
    }


    public String getPassword() {
        return this.account.getPassword();
    }

    public void LoginProcess(String email, String password) {
        DataManager.Instance(view.getApplication()).setLoginCallback(this);
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

    @Override
    public void OnLoginComplete(int code) {
        if(code==CODE_LOGIN_SUCCESS){
            if (CheckEmailVerify()){
                //setup infor curren user
                //auto store in data center
                DataManager.Instance(view.getApplicationContext()).LoadPersonByEmail(DataManager.Instance().mAuth.getCurrentUser().getEmail());

            }
            else {
                Toast. makeText(view.getApplicationContext(),"Please verify your account.",Toast. LENGTH_SHORT).show();
                view.progressBar.setIndeterminate(false);
                view.progressBar.setVisibility(View.INVISIBLE);
            }
        }

        else
        {
            Toast. makeText(view.getApplicationContext(),"Info not correct.",Toast. LENGTH_SHORT).show();
            view.progressBar.setIndeterminate(false);
            view.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void OnLoadPersonToDatacenterComplete() {
        Toast. makeText(view.getApplicationContext(),"onload person complete",Toast. LENGTH_SHORT).show();
        view.OnLoadPersonToDatacenterComplete();
    }
}