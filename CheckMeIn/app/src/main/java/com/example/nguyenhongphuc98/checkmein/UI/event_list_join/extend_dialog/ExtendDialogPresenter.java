package com.example.nguyenhongphuc98.checkmein.UI.event_list_join.extend_dialog;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class ExtendDialogPresenter {
    ExtendDialogFragment view;

    public ExtendDialogPresenter(ExtendDialogFragment view){
        this.view = view;
    }

    public Boolean SaveAttendance(String mssv) {
        return DataManager.Instance().SaveAttendance(mssv, DataCenter.EventID, "New User");
    }
}
