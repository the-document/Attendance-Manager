package com.example.nguyenhongphuc98.checkmein.UI.home;


import android.media.Image;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.Adapter.OrganAdaptor;
import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Event;
import com.example.nguyenhongphuc98.checkmein.Utils.ResizeWidthAnimation;
import com.example.nguyenhongphuc98.checkmein.UI.event.ListActivityFragment;
import com.example.nguyenhongphuc98.checkmein.UI.organ.OrganFragment;
import com.example.nguyenhongphuc98.checkmein.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IHome {

    HomePresenter presenter;

    ListActivityFragment lsActivityFragment;
    OrganFragment organFragment;
    OrganAdaptor adaptor;


    View ctnCheckIn;
    ImageView avtCheckIn;
    EditText etActivityCode;
    Button btnCreateOrgan;
    GridView gvOrgan;

    private List<ImageButton> mListOrganization;
    private RelativeLayout mOrganizationContainer;

    HashMap<String,String> lsIv=new HashMap<>();
    List<String>lsId=new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
        mListOrganization=new ArrayList<>();
        lsActivityFragment=new ListActivityFragment();
        organFragment=new OrganFragment();
        presenter=new HomePresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);


        lsIv.put("null","null");
        lsIv.put("null","null");
        lsIv.put("null","null");

        lsId.add("1");
        lsId.add("2");
        lsId.add("3");
        adaptor=new OrganAdaptor(lsIv,lsId,getActivity());

        MatchView(view);
        gvOrgan.setAdapter(adaptor);
        OnRequestLoadOrgan();

        gvOrgan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.organ_action_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_edit_organ:
                                Toast.makeText(getContext(), "edit request", Toast.LENGTH_SHORT).show();

                                DataCenter.OrganID=adaptor.getItem(i).toString();
                                DataCenter.OrganAction= DataCenter.TypeAction.EDIT;
                                Toast.makeText(getContext(),"organ: "+DataCenter.OrganID,Toast.LENGTH_SHORT).show();

                                FragmentTransaction fragmentTransition=getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransition.replace(R.id.fragment_container,organFragment);
                                fragmentTransition.commit();

                                return true;
                            case R.id.action_delete_organ:
                                Toast.makeText(getContext(), "delete request", Toast.LENGTH_SHORT).show();
                                OnRequestDeleteOrgan(adaptor.getItem(i).toString());
                                adaptor.DeleteOrgan(adaptor.getItem(i).toString());
                                return true;

                                default:
                                    return false;
                        }

                    }
                });
                return true;
            }
        });

        gvOrgan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                DataCenter.OrganID=adaptor.getItem(i).toString();
                Toast.makeText(getContext(),"organ: "+DataCenter.OrganID,Toast.LENGTH_SHORT).show();

                FragmentTransaction fragmentTransition=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransition.replace(R.id.fragment_container,lsActivityFragment);
                fragmentTransition.commit();

            }
        });


        //join event click
        ctnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avtCheckIn.setVisibility(View.INVISIBLE);
                etActivityCode.setVisibility(View.VISIBLE);

                ResizeWidthAnimation anim = new ResizeWidthAnimation(etActivityCode, 350);
                anim.setDuration(400);
                etActivityCode.startAnimation(anim);
            }
        });

        etActivityCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etActivityCode.getRight() - etActivityCode.getCompoundDrawables()[2].getBounds().width())) {
                        Toast.makeText(view.getContext(),"Joining",Toast.LENGTH_SHORT).show();
                        OnRequestJoinEvent();
                        return true;
                    }
                }
                return false;
            }
        });


        btnCreateOrgan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransition=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransition.replace(R.id.fragment_container,organFragment);
                fragmentTransition.commit();

                DataCenter.OrganAction= DataCenter.TypeAction.CREATE;
            }
        });

        return  view;
    }

    private void MatchView(View view){
        ctnCheckIn=view.findViewById(R.id.ctnCheckIn);
        avtCheckIn=view.findViewById(R.id.avtCheckIn);
        etActivityCode=view.findViewById(R.id.etActivityCode);
        btnCreateOrgan=view.findViewById(R.id.btnCreateOrgan);
        gvOrgan=view.findViewById(R.id.gvOrgan);
    }





    @Override
    public void OnRequestLoadOrgan() {
        presenter.OnRequestLoadOrgan();
    }

    @Override
    public void OnRequestJoinEvent() {
        presenter.OnRequestJoinEvent();
    }

    @Override
    public void OnRequestDeleteOrgan(String organId) {
        presenter.OnRequestDeleteOrgan(organId);
    }

    @Override
    public void OnLoadOrganSuccess() {

    }

    @Override
    public void OnJoinEventSucess(Event event) {
        Toast.makeText(getContext(),"join to: "+event.getEvent_code(),Toast.LENGTH_SHORT).show();

        //replace to view joined event with this event.
    }

    @Override
    public void OnJoinEventFail(Event event) {
        Toast.makeText(getContext(),"can't join now",Toast.LENGTH_SHORT).show();
    }
}
