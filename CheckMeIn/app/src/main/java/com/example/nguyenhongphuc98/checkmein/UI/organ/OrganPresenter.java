package com.example.nguyenhongphuc98.checkmein.UI.organ;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Organization;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

import java.util.ArrayList;

public class OrganPresenter implements IOrganView {
    private OrganFragment view;

    OrganPresenter(OrganFragment _view){
        this.view=_view;
    }

    @Override
    public void onChangePhotoClick() {
        //request view update UI
        onChangePhotoResult(CODE_CHANGE_PHOTO_SUCCESS);
    }

    @Override
    public void onAddCollaboratorClick() {

    }

    @Override
    public void onSaveOrganClick() {

        //check logic

        String des=(view.etDescription).getText().toString();
        String name=view.etNameOrgan.getText().toString();

        if(des.isEmpty()||name.isEmpty())
        {
            onSaveOrganResult(CODE_INVALID_PARAMETER);
            return;
        }

        String avatar= DataManager.Instance(view.getContext()).SaveImageToDatastore(view.avatarRUri);
        Log.e("DTM","uploadurl22:"+avatar);
        if(avatar==null||avatar.isEmpty())
        {
            onSaveOrganResult(CODE_INVALID_PARAMETER);
            return;
        }



        Organization organization=new Organization();
        organization.setId("NULL");
        organization.setAvatar(avatar);
        organization.setDescription(des);
        organization.setName(name);

        //setcurrent account login in this app
        organization.setUserId("person1");

        if(organization.Save())
            onSaveOrganResult(CODE_SAVE_ORGAN_SUCCESS);
        else
            onSaveOrganResult(CODE_SAVE_ORGAN_FAIL);
    }




    //result============================================================
    @Override
    public void showResult(String msg) {

    }

    @Override
    public void onChangePhotoResult(int code) {
        view.onChangePhotoResult(code);
        //DataManager.Instance(view.getContext()).LoadImageFromStorage("1575613702902",view.avtOrgan);

    }

    @Override
    public void onSaveOrganResult(int code) {
        view.onSaveOrganResult(code);
    }
}
