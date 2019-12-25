package com.example.nguyenhongphuc98.checkmein.UI.event;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewSetLocationFragment extends Fragment implements IViewCreateEvent{

    ViewCreatePresenter presenter;

    EditText etLocation;
    Button btnFinish;

    public ViewSetLocationFragment() {
        // Required empty public constructor
        presenter=new ViewCreatePresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_view_set_location, container, false);
        btnFinish=view.findViewById(R.id.btnFinishCreateActivity);
        etLocation=view.findViewById(R.id.etLocation);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //ViewPager pager = (ViewPager) view.getParent();
                //ager.setCurrentItem(0);
                //Toast.makeText(getContext(),"Add success!!",Toast.LENGTH_SHORT).show();

                DataCenter.eventLocation=etLocation.getText().toString();

                OnCreateEventRequest();
            }
        });

        return view;
    }



    //======================================================
    @Override
    public void OnCreateEventRequest() {
        presenter.OnCreateEventRequest();
    }

    @Override
    public void OnCreateEventSuccess() {
        Toast.makeText(getContext(),"Save success.",Toast.LENGTH_SHORT).show();
        ViewPager pager = (ViewPager) getView().getParent();
        pager.setCurrentItem(0);

    }

    @Override
    public void OnCreateEventFail(int code) {
        switch (code){
            case IViewCreateEvent.CODE_INVALID_ARGUMENT:
                Toast.makeText(getContext(),"invalid infor.",Toast.LENGTH_SHORT).show();
                break;
            case IViewCreateEvent.CODE_DB_ERROR:
                Toast.makeText(getContext(),"Error connect db.",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
