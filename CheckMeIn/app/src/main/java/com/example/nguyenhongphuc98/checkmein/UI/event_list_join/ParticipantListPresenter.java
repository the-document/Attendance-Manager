package com.example.nguyenhongphuc98.checkmein.UI.event_list_join;

import com.example.nguyenhongphuc98.checkmein.CreateActivity.ViewListParticipantFragment;
import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class ParticipantListPresenter {
    ParticipantListFragment view;

    public ParticipantListPresenter(ParticipantListFragment view){
        this.view = view;
    }

    public void OnLoadListAttendance() {
        Boolean b= DataManager.Instance().LoadAttendanceByEvent(view.lsAttendance);
        if(b)
            view.OnLoadListEventSuccess();
        else
            view.OnLoadListEventFail();
    }
}
