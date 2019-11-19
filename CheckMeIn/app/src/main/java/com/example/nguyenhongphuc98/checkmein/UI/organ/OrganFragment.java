package com.example.nguyenhongphuc98.checkmein.UI.organ;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.adapter.CollaborationAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrganFragment extends Fragment implements IOrganView{

    private OrganPresenter presenter;

    GridView gvCollaborator;
    CircularImageView avtOrgan;
    EditText etBameOrgan;
    EditText etDescription;
    EditText etCollaborator;
    Button btnSave;
    Button btnChangePhoto;

    CollaborationAdapter adapter;

    List<String> lsCollaborator;

    public OrganFragment() {
        // Required empty public constructor
       lsCollaborator=new ArrayList<String>();
       lsCollaborator.add("a");
       lsCollaborator.add("a");
       lsCollaborator.add("a");

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

        return  view;
    }

    private void MatchView( View v){
        gvCollaborator=v.findViewById(R.id.gvCollaborator);
        avtOrgan=v.findViewById(R.id.avatar_organ);
        etBameOrgan=v.findViewById(R.id.etOrganName);
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
                //save data organ
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
                        lsCollaborator.add("templink");
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onChangePhotoClick() {
        presenter.onChangePhotoClick();
    }

    @Override
    public void onAddCollaboratorClick() {

    }

    @Override
    public void onSaveOrganClick() {

    }

    @Override
    public void showResult(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }
}
