package com.example.nguyenhongphuc98.checkmein.UI.user;

import android.view.View;

public class InforFragmentPresenter implements IInforFragmentView{

    private InfoFragment view;

    InforFragmentPresenter(InfoFragment _view){
        this.view=_view;
    }

    @Override
    public void OnSaveDisplayName(String _displayName) {
        //check valid name
    }

    @Override
    public void OnShowResult() {

    }
}
