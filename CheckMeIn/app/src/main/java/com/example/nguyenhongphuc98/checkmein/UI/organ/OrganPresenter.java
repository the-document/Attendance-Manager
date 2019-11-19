package com.example.nguyenhongphuc98.checkmein.UI.organ;

import android.view.View;

public class OrganPresenter implements IOrganView {
    private OrganFragment view;

    OrganPresenter(OrganFragment _view){
        this.view=_view;
    }

    @Override
    public void onChangePhotoClick() {
        //access firebase by DataManager
        //process logic
        //request view update UI
        view.showResult("change success");
    }

    @Override
    public void onAddCollaboratorClick() {

    }

    @Override
    public void onSaveOrganClick() {

    }

    @Override
    public void showResult(String msg) {

    }
}
