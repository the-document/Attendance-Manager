package com.example.nguyenhongphuc98.checkmein.UI.user;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.example.nguyenhongphuc98.checkmein.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.Adapter.PageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mikhaellopez.circularimageview.CircularImageView;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements IInforFragmentView {
    //define constant;
    final int CODE_OPEN_DOCUMENT = 22;

    InforFragmentPresenter presenter;

    EditText mEtName;
    EditText mEtMSSV;
    Button mBtnEditName;
    CircularImageView avatar;

    String mOldName;
    Boolean mNameEditing;

    PageAdapter mPageAdapter;
    ViewPager mViewPaper;

    TabLayout mTabLayout;
    TabItem mTiActivity;
    TabItem mTiAccount;

    FirebaseUser user;
    UserProfileChangeRequest profileUpdates;
    Account account;

    Uri avatarRUri;

    public Boolean isImageChange=false;

    public InfoFragment() {
        // Required empty public constructor
        mNameEditing=false;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        presenter=new InforFragmentPresenter(this);

        mTabLayout=view.findViewById(R.id.tlInfo);
        mTiActivity=view.findViewById(R.id.tiAccount);
        mTiAccount=view.findViewById(R.id.tiAccount);

        mEtName=view.findViewById(R.id.etNameUser);
        mBtnEditName=view.findViewById(R.id.btnEditName);
        mEtMSSV=view.findViewById(R.id.etMSSV);
        avatar=view.findViewById(R.id.avataruser);

        mViewPaper=view.findViewById(R.id.vpInfor);
        mPageAdapter=new PageAdapter(getChildFragmentManager(),mTabLayout.getTabCount());
        mViewPaper.setAdapter(mPageAdapter);

        if (user.getDisplayName() != null) {
            String name = user.getDisplayName();
            mEtName.setText(name);
        }

        addEvent();

        OnInitInfo();

        return view;
    }



    void addEvent(){
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPaper.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        mViewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        mEtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //don't allow modify other account
                if(mEtMSSV.getText().toString().isEmpty())
                {
                    mBtnEditName.setBackgroundResource(R.drawable.icon_edit);
                    mBtnEditName.setVisibility(View.VISIBLE);
                }

            }
        });

        // Update user name
        mBtnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mNameEditing){

                    mEtName.setFocusableInTouchMode(true);
                    mEtName.setFocusable(true);
                    mEtName.requestFocus();
                    mEtName.setSelection(mEtName.getText().length());

                    mBtnEditName.setBackgroundResource(R.drawable.icon_check);
                    mNameEditing=true;
                    mOldName=mEtName.getText().toString();
                }
                else {
                    mEtName.setFocusableInTouchMode(false);
                    mEtName.setFocusable(false);

                    mBtnEditName.setVisibility(View.INVISIBLE);
                    mNameEditing=false;

                    Log.e("AAAA","here1");
                    //check save data
                    String newName=mEtName.getText().toString();
                    if(newName.equals(mOldName))
                        return;
                    Log.e("AAAA","here2");
                    //save new name here
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(newName)
                            .build();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User name updated.");
                                    }
                                }
                            });

                    //update new name in person Table
                    OnUpdateNameOfUser();
                }
            }
        });

        mEtMSSV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (mEtMSSV.getRight() - mEtMSSV.getCompoundDrawables()[2].getBounds().width())) {
                        mEtMSSV.setText("");

                        return true;
                    }
                }
                return false;
            }
        });

        mEtMSSV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                OnQueryData();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //just modify of owne
                if(mEtMSSV.getText().toString().length() != 0)
                    return;

                //get image from device
                Intent intentOpenFile=new Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentOpenFile,"Choose image"),CODE_OPEN_DOCUMENT);
            }
        });
    }




    //=============================================================
    @Override
    public void OnInitInfo() {
        //get display name and avatart
        presenter.OnInitInfo();
    }


    @Override
    public void OnSaveDisplayName(String _displayName) {

    }


    @Override
    public void OnQueryData() {
        presenter.OnQueryData();
    }

    @Override
    public void OnUpdateNameOfUser() {
        presenter.OnUpdateNameOfUser();
    }

    @Override
    public void OnShowEvent(int code) {

        switch (code){
            case CODE_SUCCESS:
                Toast.makeText(getContext(),"finding...",Toast.LENGTH_SHORT).show();
                break;
            case CODE_NOT_FOUND:
                Toast.makeText(getContext(),"not found",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void OnUpdatedDisplayName(int code) {
        switch (code){
            case CODE_SUCCESS:
                Toast.makeText(getContext(),"Update success",Toast.LENGTH_SHORT).show();
                break;
            case CODE_FAIL:
                Toast.makeText(getContext(),"Fail",Toast.LENGTH_SHORT).show();
                break;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CODE_OPEN_DOCUMENT&&resultCode==RESULT_OK){

            //show image on avatar
            Uri selectedFile=data.getData();
            Log.d("PhotoURL","photo: "+selectedFile.toString());
            avatarRUri=selectedFile;
            //Toast.makeText(getContext(),selectedFile.toString(), Toast.LENGTH_LONG).show();
            isImageChange=true;
            avatar.setImageURI(avatarRUri);
            presenter.onChangePhotoClick();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        mEtMSSV.setText("");
        //Log.e("USER","stop");
    }
}
