package com.example.nguyenhongphuc98.checkmein;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.adapter.EventAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragmentAct extends Fragment {

    ListView listView;
    EventAdapter adapter;
    String temp1[]={"a","b","c","d","d","d","d"};

    private static InfoFragmentAct _instance=null;

    public static InfoFragmentAct GetInstance(){
        if(_instance==null)
            _instance=new InfoFragmentAct();
        return  _instance;
    }


    private static InfoFragmentAct _instance=null;

    public static InfoFragmentAct GetInstance(){
        if(_instance==null)
            _instance=new InfoFragmentAct();
        return  _instance;
    }


    public InfoFragmentAct() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info_act, container, false);

        listView=view.findViewById(R.id.lvEventOfInfo);
        adapter=new EventAdapter(getContext(),temp1);
        listView.setAdapter(adapter);

        return view;
    }

}
