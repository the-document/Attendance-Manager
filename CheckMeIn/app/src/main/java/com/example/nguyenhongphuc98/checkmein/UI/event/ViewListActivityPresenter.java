package com.example.nguyenhongphuc98.checkmein.UI.event;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class ViewListActivityPresenter implements IViewListActivity{

    ViewListActivityFragment view;

    public ViewListActivityPresenter(ViewListActivityFragment view) {
        this.view = view;
    }

    @Override
    public void OnLoadListEvent() {

        Boolean b= DataManager.Instance().LoadActivitys(view.lsEvent, DataCenter.OrganID,view.adapter);
        if(b)
            OnLoadListEventSuccess();
        else
            OnLoadListEventFail();
    }

    @Override
    public void OnLoadListEventSuccess() {
        view.OnLoadListEventSuccess();
    }

    @Override
    public void OnLoadListEventFail() {
        view.OnLoadListEventFail();
    }
}
