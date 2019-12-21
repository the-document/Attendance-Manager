package com.example.nguyenhongphuc98.checkmein.UI.user;

import android.view.View;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Person;

public class InforFragmentPresenter implements IInforFragmentView{

    private InfoFragment view;
    private Account account;
    private Person person;

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
