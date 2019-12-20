package com.example.nguyenhongphuc98.checkmein.UI.organ;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Collaborator;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Organization;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.mikhaellopez.circularimageview.CircularImageView;

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


        DataManager.Instance().LoadImageCollorator(
                view.etCollaborator.getText().toString(),
                view.lsCollaborator,view.adapter);

        view.mssvCollaborators.add(view.etCollaborator.getText().toString());
        view.etCollaborator.setText("");
    }

    public void SaveCollborator(){
        for(int i=0;i<view.mssvCollaborators.size();i++){
            Collaborator cola=new Collaborator(view.mssvCollaborators.get(i),DataCenter.UserID, DataCenter.OrganID);

            cola.Save();
        }
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
        organization.setId("null");
        organization.setAvatar(avatar);
        organization.setDescription(des);
        organization.setName(name);

        //setcurrent account login in this app
        organization.setUserId(DataCenter.UserID);
        SaveCollborator();

        if(organization.Save())
            onSaveOrganResult(CODE_SAVE_ORGAN_SUCCESS);
        else
            onSaveOrganResult(CODE_SAVE_ORGAN_FAIL);


    }

    @Override
    public void OnEditOrganClick() {
        String des=(view.etDescription).getText().toString();
        String name=view.etNameOrgan.getText().toString();

        if(des.isEmpty()||name.isEmpty())
        {
            onSaveOrganResult(CODE_INVALID_PARAMETER);
            return;
        }

        String avatar=view.avatarid.getText().toString();
        if(view.isImageChange)
        {
            avatar=DataManager.Instance(view.getContext()).SaveImageToDatastore(view.avatarRUri);
            Log.e("DTM","uploadurl22:"+avatar);
        }

        if(avatar==null||avatar.isEmpty())
        {
            onSaveOrganResult(CODE_INVALID_PARAMETER);
            return;
        }




        Organization organization=new Organization();
        organization.setId(DataCenter.OrganID);
        organization.setAvatar(avatar);
        organization.setDescription(des);
        organization.setName(name);

        //setcurrent account login in this app
        organization.setUserId(DataCenter.UserID);
        //SaveCollborator();

        if(DataManager.Instance().EditOrgan(organization)==true)
            OnEditOrganResult(CODE_SAVE_ORGAN_SUCCESS);
        else
            OnEditOrganResult(CODE_SAVE_ORGAN_FAIL);
    }

    @Override
    public void LoadOrganInfo() {
        DataManager.Instance().LoadOrganByID(DataCenter.OrganID,view.avtOrgan,view.etNameOrgan,view.etDescription,view.avatarid);
    }


    //result============================================================
    @Override
    public void showResult(String msg) {

    }

    @Override
    public void onChangePhotoResult(int code) {
        view.onChangePhotoResult(code);


//        CircularImageView i=new CircularImageView(view.getContext());
//        DataManager.Instance(view.getContext()).LoadImageFromStorage("1575613702902",i);
//        view.avtOrgan= i;

        //DataManager.Instance(view.getContext()).LoadImageFromStorage("1575613702902",view.avtOrgan);

    }

    @Override
    public void onSaveOrganResult(int code) {
        view.onSaveOrganResult(code);
    }

    @Override
    public void OnEditOrganResult(int code) {
        view.OnEditOrganResult(code);
    }
}
