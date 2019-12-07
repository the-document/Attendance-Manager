package com.example.nguyenhongphuc98.checkmein.UI.home;

import android.util.Log;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

import java.util.List;

public class HomePresenter implements IHome{
    HomeFragment view;

    public HomePresenter(HomeFragment v){
        view=v;
    }

    @Override
    public void OnRequestLoadOrgan() {
        DataManager.Instance().LoadOrgan(view.adaptor,view.lsId,view.lsIv, DataCenter.UserID);
    }

    @Override
    public void OnLoadOrganSuccess() {

    }
}
