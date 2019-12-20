package com.example.nguyenhongphuc98.checkmein.UI.organ;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.home.HomeFragment;
import com.example.nguyenhongphuc98.checkmein.adapter.CollaborationAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrganFragment extends Fragment implements IOrganView{

    //define constant;
    final int CODE_OPEN_DOCUMENT = 22;

    private OrganPresenter presenter;

    GridView gvCollaborator;
    CircularImageView avtOrgan;
    EditText etNameOrgan;
    EditText etDescription;
    EditText etCollaborator;
    Button btnSave;
    Button btnChangePhoto;

    CollaborationAdapter adapter;

    List<String> lsCollaborator;
    List<String> mssvCollaborators;
    Uri avatarRUri;

    public Boolean isImageChange=false;
    public TextView avatarid;

    public OrganFragment() {
        // Required empty public constructor

       lsCollaborator=new ArrayList<>();
        mssvCollaborators=new ArrayList<>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_organ, container, false);
        MatchView(view);
        adapter=new CollaborationAdapter(lsCollaborator,getContext());
        gvCollaborator.setAdapter(adapter);
        SetEvent(view);

        presenter=new OrganPresenter(this);
        avatarid=new TextView(getContext());

        //load current organ
        if(DataCenter.OrganAction== DataCenter.TypeAction.EDIT){
            LoadOrganInfo();
        }

        return  view;
    }

    private void MatchView( View v){
        gvCollaborator=v.findViewById(R.id.gvCollaborator);
        avtOrgan=v.findViewById(R.id.avatar_organ);
        etNameOrgan=v.findViewById(R.id.etOrganName);
        etDescription=v.findViewById(R.id.etOrganDes);
        etCollaborator=v.findViewById(R.id.etOrganCollaborator);
        btnSave=v.findViewById(R.id.btnSaveOrgan);
        btnChangePhoto=v.findViewById(R.id.btnChangePhoto);
    }

    private void SetEvent(final View view){

        btnChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangePhotoClick();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataCenter.OrganAction== DataCenter.TypeAction.CREATE)
                    onSaveOrganClick();
                else {
                    //this is edit part
                    if(DataCenter.OrganAction== DataCenter.TypeAction.EDIT)
                        OnEditOrganClick();
                }

                DataCenter.OrganAction=DataCenter.TypeAction.CREATE;
            }
        });


        etCollaborator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etCollaborator.getRight() - etCollaborator.getCompoundDrawables()[2].getBounds().width())) {

                        //check empty
                        if( etCollaborator.getText().equals("")||etCollaborator.getText()==null){
                            Toast.makeText(view.getContext(),"Please fill ID",Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        //get link by ID user
                        //lsCollaborator.add("");
                        presenter.onAddCollaboratorClick();

                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onChangePhotoClick() {
        //get image from device
        Intent intentOpenFile=new Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentOpenFile,"Choose image"),CODE_OPEN_DOCUMENT);

    }

    @Override
    public void onAddCollaboratorClick() {

    }

    @Override
    public void onSaveOrganClick() {
        presenter.onSaveOrganClick();
    }

    @Override
    public void OnEditOrganClick() {
        presenter.OnEditOrganClick();
    }


    //==================================================================
    @Override
    public void showResult(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChangePhotoResult(int code) {
        if(code==CODE_CHANGE_PHOTO_SUCCESS)
            avtOrgan.setImageURI(avatarRUri);
        else
            Toast.makeText(getContext(),"can't set avatar!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveOrganResult(int code) {
        switch (code)
        {
            case CODE_SAVE_ORGAN_SUCCESS:
                Toast.makeText(getContext(),"save success.",Toast.LENGTH_SHORT).show();

                FragmentTransaction fragmentTransition=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransition.replace(R.id.fragment_container,new HomeFragment());
                fragmentTransition.commit();

                break;
            case CODE_SAVE_ORGAN_FAIL:
                Toast.makeText(getContext(),"fail to save.",Toast.LENGTH_SHORT).show();
                break;
            case CODE_INVALID_PARAMETER:
                Toast.makeText(getContext(),"infor not correct.",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void OnEditOrganResult(int code) {
        switch (code)
        {
            case CODE_SAVE_ORGAN_SUCCESS:
                Toast.makeText(getContext(),"edit success.",Toast.LENGTH_SHORT).show();

                FragmentTransaction fragmentTransition=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransition.replace(R.id.fragment_container,new HomeFragment());
                fragmentTransition.commit();

                break;
            case CODE_SAVE_ORGAN_FAIL:
                Toast.makeText(getContext(),"fail to edit.",Toast.LENGTH_SHORT).show();
                break;
            case CODE_INVALID_PARAMETER:
                Toast.makeText(getContext(),"infor not correct.",Toast.LENGTH_SHORT).show();
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
            presenter.onChangePhotoClick();
        }

    }

    public void onShowProfile(Bitmap bitmap){
        this.avtOrgan.setImageBitmap(bitmap);
    }

    @Override
    public void LoadOrganInfo(){
        presenter.LoadOrganInfo();
    }
}
