package com.example.nguyenhongphuc98.checkmein.UI.user.account;

import android.util.Log;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.UI.user.IInforFragmentView;

public class InfoAccountPresenter implements IInfoAccount {

    InfoFragmentAccount view;


    public InfoAccountPresenter(InfoFragmentAccount view) {
        this.view = view;
    }

    @Override
    public void OnInitAccountInfo() {
        DataManager.Instance().LoadUserByID(DataCenter.UserID,view.etEmail,view.etPhone,view.tvMssv);
    }

    @Override
    public void OnUpdatePhoneNumber() {

        Boolean result = DataManager.Instance().UpdatePersonPhoneNumberByID(DataCenter.UserID,view.etPhone.getText().toString());
        if(result)
            view.OnUpdatePhoneResult(CODE_UPDATE_PHONE_SUCCESS);
        else
            view.OnUpdatePhoneResult(CODE_UPDATE_PHONE_FAIL);
    }

    @Override
    public void OnUpdatePhoneResult(int code) {
        view.OnUpdatePhoneResult(code);
    }
}
