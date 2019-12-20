package com.example.nguyenhongphuc98.checkmein.UI.home;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Event;

public interface IHome {
    void OnRequestLoadOrgan();
    void OnRequestJoinEvent();

    void OnLoadOrganSuccess();
    void OnJoinEventSucess(Event event);
    void OnJoinEventFail(Event event);
}
