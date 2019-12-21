package com.example.nguyenhongphuc98.checkmein.UI.event;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Event;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class ViewCreatePresenter implements IViewCreateEvent {

    ViewSetLocationFragment view;

    public ViewCreatePresenter(ViewSetLocationFragment view) {
        this.view = view;
    }

    @Override
    public void OnCreateEventRequest() {
        Event e=new Event();
        if(DataCenter.eventName.isEmpty()
                ||DataCenter.eventCode.isEmpty()
                ||DataCenter.eventBegin.isEmpty()
                ||DataCenter.eventEnd.isEmpty()
                ||DataCenter.eventDay.isEmpty()
                ||DataCenter.eventLocation.isEmpty()
        )
        {
            OnCreateEventFail(IViewCreateEvent.CODE_INVALID_ARGUMENT);
            return;
        }

        e.setAvatar("not init url yet");
        e.setBegin_time(DataCenter.eventBegin);
        e.setEnd_time(DataCenter.eventEnd);
        e.setEvent_code(DataCenter.eventCode);
        e.setEvent_day(DataCenter.eventDay);
        e.setEvent_name(DataCenter.eventName);
        e.setLocation(DataCenter.eventLocation);
        e.setOrgan(DataCenter.OrganID);

        if(DataManager.Instance().SaveEvent(e))
            OnCreateEventSuccess();
        else
            OnCreateEventFail(IViewCreateEvent.CODE_DB_ERROR);
    }

    @Override
    public void OnCreateEventSuccess() {
        view.OnCreateEventSuccess();
    }

    @Override
    public void OnCreateEventFail(int code) {
        view.OnCreateEventFail(code);
    }
}
