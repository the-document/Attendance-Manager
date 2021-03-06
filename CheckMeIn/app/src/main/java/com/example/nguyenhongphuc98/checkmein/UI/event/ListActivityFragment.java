package com.example.nguyenhongphuc98.checkmein.UI.event;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.adapter.PageAdapterListActivity;


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
