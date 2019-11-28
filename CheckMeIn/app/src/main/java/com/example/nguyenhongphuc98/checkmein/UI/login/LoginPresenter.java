package com.example.nguyenhongphuc98.checkmein.UI.login;

import android.view.View;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class LoginPresenter implements ILoginView {
    private Account account;
    private View view;
    private DataManager dataManager;

    public LoginPresenter(View view) {
        this.account = new Account();
        this.view = view;
        dataManager = new DataManager();
    }

    public void setAccount(String email, String password) {
        account.setMail(email);
        account.setPassword(password);
    }

    public void LoginProcess(String email, String password) {
        dataManager.ProcessLogin(email, password);
    }

    public boolean CheckLoginStatus() {
        return dataManager.checkLoginStatus();
    }

    public interface View{

    }
}
