package com.example.nguyenhongphuc98.checkmein.UI.user;


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
public class InfoFragmentAct extends Fragment {

    ListView listView;
    EventAdapter adapter;
    List<Event> lsEvent;

    public InfoFragmentAct() {
        // Required empty public constructor
        lsEvent=new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info_act, container, false);

        listView=view.findViewById(R.id.lvEventOfInfo);
        adapter=new EventAdapter(getContext(),lsEvent);
        listView.setAdapter(adapter);

        return view;
    }

}
