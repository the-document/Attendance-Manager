package com.example.nguyenhongphuc98.checkmein.UI.user.account;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragmentAccount extends Fragment  implements IInfoAccount{

    InfoAccountPresenter presenter;

    Button btnEditEmail;
    Button btnEditPhone;
    public EditText etEmail;
    public EditText etPhone;
    public TextView tvMssv;

    boolean mEmailEditing;
    boolean mPhoneEditing;

    String mOldEmail;
    String mOldPhone;

    public InfoFragmentAccount() {
        // Required empty public constructor
        mEmailEditing=false;
        mPhoneEditing=false;
    }

    private static InfoFragmentAccount instance;

    public static InfoFragmentAccount Instance(){
        if(instance==null){
            instance=new InfoFragmentAccount();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info_account, container, false);

        btnEditEmail =view.findViewById(R.id.btnEditEmail);
        btnEditPhone=view.findViewById(R.id.btnEditPhone);
        etEmail=view.findViewById(R.id.etEmail);
        etPhone=view.findViewById(R.id.etPhone);
        tvMssv=view.findViewById(R.id.tvmssv);

       addEvent();

        presenter=new InfoAccountPresenter(this);
        OnInitAccountInfo();
        return  view;
    }

    void addEvent(){
        etEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditEmail.setBackgroundResource(R.drawable.icon_edit);
                btnEditEmail.setVisibility(View.VISIBLE);
            }
        });

        etPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditPhone.setBackgroundResource(R.drawable.icon_edit);
                btnEditPhone.setVisibility(View.VISIBLE);
            }
        });

        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEmailEditing){

                    etEmail.setFocusableInTouchMode(true);
                    etEmail.setFocusable(true);
                    etEmail.requestFocus();
                    etEmail.setSelection(etEmail.getText().length());

                    btnEditEmail.setBackgroundResource(R.drawable.icon_check);
                    mEmailEditing=true;
                    mOldEmail=etEmail.getText().toString();
                }
                else {
                    etEmail.setFocusableInTouchMode(false);
                    etEmail.setFocusable(false);

                    btnEditEmail.setVisibility(View.INVISIBLE);
                    mEmailEditing=false;

                    //check save data
                    String newEmail=etEmail.getText().toString();
                    if(newEmail.equals(mOldEmail))
                        return;

                    //save new email here
                    //========================
                }

            }
        });

        btnEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mPhoneEditing){

                    etPhone.setFocusableInTouchMode(true);
                    etPhone.setFocusable(true);
                    etPhone.requestFocus();
                    etPhone.setSelection(etPhone.getText().length());

                    btnEditPhone.setBackgroundResource(R.drawable.icon_check);
                    mPhoneEditing=true;
                    mOldPhone=etEmail.getText().toString();
                }
                else {
                    etPhone.setFocusableInTouchMode(false);
                    etPhone.setFocusable(false);

                    btnEditPhone.setVisibility(View.INVISIBLE);
                    mPhoneEditing=false;

                    //check save data
                    String newPhone=etPhone.getText().toString();
                    if(newPhone.equals(mOldPhone))
                        return;

                    //save new phone here
                    //========================
                }

            }
        });
    }

    @Override
    public void OnInitAccountInfo() {
        Log.e("AAAA","1");
        presenter.OnInitAccountInfo();
    }
}
