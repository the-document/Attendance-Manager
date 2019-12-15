package com.example.nguyenhongphuc98.checkmein.UI.user.activity;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Event;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.Adapter.EventAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragmentAct extends Fragment implements IInfoActivity {

    InfoActivityPresenter presenter;

    ListView listView;
    public EventAdapter adapter;
    public List<Event> lsEvent;

    private static InfoFragmentAct instance;
    public static InfoFragmentAct Instance(){
        if(instance==null)
            instance=new InfoFragmentAct();

        return instance;
    }

    public InfoFragmentAct() {
        // Required empty public constructor
        lsEvent=new ArrayList<>();
        Event e=new Event("ava","9:30","11:00",
                "CODEXC","location","organ",
                "id","22-12","Huongn ghiep 2019");
        lsEvent.add(e);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info_act, container, false);

        listView=view.findViewById(R.id.lvEventOfInfo);
        adapter=new EventAdapter(getContext(),lsEvent);
        listView.setAdapter(adapter);

        presenter=new InfoActivityPresenter(this);

        OnInitActivityInfo();
        return view;
    }


    @Override
    public void OnInitActivityInfo() {
        presenter.OnInitActivityInfo();
    }
}
