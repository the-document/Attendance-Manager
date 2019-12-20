package com.example.nguyenhongphuc98.checkmein.UI.home;

import android.util.Log;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Event;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

import java.util.List;

public class HomePresenter implements IHome, IEventCallBack{
    HomeFragment view;

    public HomePresenter(HomeFragment v){
        view=v;
    }

    @Override
    public void OnRequestLoadOrgan() {
        DataManager.Instance().LoadOrgan(view.adaptor,view.lsId,view.lsIv, DataCenter.UserID);
    }

    @Override
    public void OnRequestJoinEvent() {
        DataManager.Instance().setEventCallBacks(this);
        DataManager.Instance().LoadEventByCode(view.etActivityCode.getText().toString());
    }

    @Override
    public void OnLoadOrganSuccess() {

    }

    @Override
    public void OnJoinEventSucess(Event event) {

    }

    @Override
    public void OnJoinEventFail(Event event) {

    }

    @Override
    public void OnLoadEventComplete(Event event) {
        if(event!=null)
            view.OnJoinEventSucess(event);
        else
            view.OnJoinEventFail(event);
    }
}
