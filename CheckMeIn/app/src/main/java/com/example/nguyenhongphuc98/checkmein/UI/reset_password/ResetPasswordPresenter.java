package com.example.nguyenhongphuc98.checkmein.UI.reset_password;

import android.view.View;

import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.google.android.gms.tasks.Task;

public class ResetPasswordPresenter {
    DataManager dataManager;
    IResetPasswordView view;

    public ResetPasswordPresenter(IResetPasswordView view) {
        dataManager = new DataManager();
        this.view = view;
    }

    public Task sendMailResetPassword(String email) {
        return dataManager.sendMailResetPassword(email);
    }
}
