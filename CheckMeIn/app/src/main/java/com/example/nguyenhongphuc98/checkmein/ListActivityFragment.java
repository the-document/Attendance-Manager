package com.example.nguyenhongphuc98.checkmein;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.Adapter.EventAdapter;
import com.example.nguyenhongphuc98.checkmein.Adapter.PageAdapterListActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListActivityFragment extends Fragment {

    ViewPager activityViewPager;
    PageAdapterListActivity pageAdapterListActivity;

    View vCreateAcivity;

    public ListActivityFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list_activity, container, false);

        activityViewPager=view.findViewById(R.id.vpActivity);
        vCreateAcivity=view.findViewById(R.id.viewCreateACtivity);

        pageAdapterListActivity=new PageAdapterListActivity(getChildFragmentManager(),4);
        activityViewPager.setAdapter(pageAdapterListActivity);
        activityViewPager.setCurrentItem(0);

        vCreateAcivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityViewPager.setCurrentItem(1);
            }
        });

        return view;
    }

}
