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
        Log.e("AAAA","2");
        DataManager.Instance().LoadUserByID(DataCenter.UserID,view.etEmail,view.etPhone,view.tvMssv);
    }
}
