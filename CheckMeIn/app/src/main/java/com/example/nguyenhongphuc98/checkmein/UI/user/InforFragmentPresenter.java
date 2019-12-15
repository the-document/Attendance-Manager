package com.example.nguyenhongphuc98.checkmein.UI.user;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.user.account.InfoAccountPresenter;
import com.example.nguyenhongphuc98.checkmein.UI.user.account.InfoFragmentAccount;
import com.example.nguyenhongphuc98.checkmein.UI.user.activity.InfoFragmentAct;

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
    public void OnInitInfo() {

        //get display name and avatart
        DataManager.Instance().LoadGenarelInfoPersonByID(DataCenter.UserID,view.mEtName,view.avatar);
    }

    @Override
    public void OnSaveDisplayName(String _displayName) {
        //check valid name
    }



    @Override
    public void OnQueryData() {
        String mssv=view.mEtMSSV.getText().toString();

        //reset info of current user
        if (mssv.length()==0){

            //reset genral info
            OnInitInfo();

            //reset activity
            Boolean r=DataManager.Instance().LoadActivitysOfUser(InfoFragmentAct.Instance().lsEvent,DataCenter.UserID,InfoFragmentAct.Instance().adapter);

            if(r)
                OnShowEvent(CODE_SUCCESS);
            else
                OnShowEvent(CODE_NOT_FOUND);


            //reset info account
            DataManager.Instance().LoadUserByID(DataCenter.UserID,
                    InfoFragmentAccount.Instance().etEmail,
                    InfoFragmentAccount.Instance().etPhone,
                    InfoFragmentAccount.Instance().tvMssv);

            return;
        }

        if(mssv.length()==1)
        {
            //clear current user info
            view.mEtName.setText("Finding...");
            view.avatar.setImageResource(R.drawable.avatar);

            //clear activity
            InfoFragmentAct.Instance().lsEvent.clear();
            InfoFragmentAct.Instance().adapter.notifyDataSetChanged();

            //clear userinfo
            InfoFragmentAccount.Instance().etEmail.setText("finding...");
            InfoFragmentAccount.Instance().etPhone.setText("finding...");
            InfoFragmentAccount.Instance().tvMssv.setText("finding...");
            return;
        }



        if (mssv.length()==8){

            //load genral info
            DataManager.Instance().LoadGenarelInfoPersonByID(mssv,view.mEtName,view.avatar);

           //load activity
           Boolean r=DataManager.Instance().LoadActivitysOfUser(InfoFragmentAct.Instance().lsEvent,mssv,InfoFragmentAct.Instance().adapter);
           if(r)
               OnShowEvent(CODE_SUCCESS);
           else
               OnShowEvent(CODE_NOT_FOUND);

            //reset info account
            DataManager.Instance().LoadUserByID(mssv,
                    InfoFragmentAccount.Instance().etEmail,
                    InfoFragmentAccount.Instance().etPhone,
                    InfoFragmentAccount.Instance().tvMssv);
        }

    }

    @Override
    public void OnUpdateNameOfUser() {
        Boolean r= DataManager.Instance().UpdatePersonDisplayNameByID(DataCenter.UserID,view.mEtName.getText().toString());

        if(r)
            OnUpdatedDisplayName(CODE_SUCCESS);
        else OnUpdatedDisplayName(CODE_FAIL);
    }


    @Override
    public void OnShowEvent(int code) {
        view.OnShowEvent(code);
    }

    @Override
    public void OnUpdatedDisplayName(int code) {
        view.OnUpdatedDisplayName(code);
    }
}
