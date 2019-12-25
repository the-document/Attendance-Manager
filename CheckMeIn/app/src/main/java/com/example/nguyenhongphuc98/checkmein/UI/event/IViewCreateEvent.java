package com.example.nguyenhongphuc98.checkmein.UI.event;

public interface IViewCreateEvent {

    final public int CODE_INVALID_ARGUMENT=0;
    final public int CODE_DB_ERROR=1;

    public void OnCreateEventRequest();

    public void OnCreateEventSuccess();
    public void OnCreateEventFail(int code);
}
