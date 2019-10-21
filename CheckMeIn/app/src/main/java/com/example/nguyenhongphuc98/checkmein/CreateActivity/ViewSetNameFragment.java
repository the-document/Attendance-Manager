package com.example.nguyenhongphuc98.checkmein.CreateActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nguyenhongphuc98.checkmein.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewSetNameFragment extends Fragment {

    Button btnNextToTime;

    private static ViewSetNameFragment _instance=null;

    public static ViewSetNameFragment GetInstance(){
        if(_instance==null)
            _instance=new ViewSetNameFragment();
        return  _instance;
    }

    public ViewSetNameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_view_set_name, container, false);

        btnNextToTime=view.findViewById(R.id.btnNextToTime);

        btnNextToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager pager = (ViewPager) view.getParent();
                pager.setCurrentItem(2);
            }
        });

        return view;
    }

}
