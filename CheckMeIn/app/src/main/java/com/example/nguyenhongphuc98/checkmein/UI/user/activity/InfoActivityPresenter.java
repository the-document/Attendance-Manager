package com.example.nguyenhongphuc98.checkmein.UI.user.activity;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class InfoActivityPresenter implements IInfoActivity {

    InfoFragmentAct view;

    public InfoActivityPresenter(InfoFragmentAct view) {
        this.view = view;
    }

    @Override
    public void OnInitActivityInfo() {
        Boolean r= DataManager.Instance().LoadActivitysOfUser(InfoFragmentAct.Instance().lsEvent, DataCenter.UserID,InfoFragmentAct.Instance().adapter);
    }
}
