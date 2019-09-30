package com.example.nguyenhongphuc98.checkmein;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.Adapter.EventAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListActivityFragment extends Fragment {

    ListView listView;
    EventAdapter adapter;
    String temp1[]={"a","b","c","d"};

    public ListActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list_activity, container, false);

        listView=view.findViewById(R.id.lvEventOfOrganization);
        adapter=new EventAdapter(getContext(),temp1);
        listView.setAdapter(adapter);

        return view;
    }

}
