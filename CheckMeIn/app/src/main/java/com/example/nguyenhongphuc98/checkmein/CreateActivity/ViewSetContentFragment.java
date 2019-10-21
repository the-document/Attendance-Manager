package com.example.nguyenhongphuc98.checkmein.CreateActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewSetContentFragment extends Fragment {

    Button btnFinish;

    private static ViewSetContentFragment _instance=null;

    public static ViewSetContentFragment GetInstance(){
        if(_instance==null)
            _instance=new ViewSetContentFragment();
        return  _instance;
    }

    public ViewSetContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_view_set_content, container, false);
        btnFinish=view.findViewById(R.id.btnFinishCreateActivity);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager pager = (ViewPager) view.getParent();
                pager.setCurrentItem(0);
                Toast.makeText(getContext(),"Add success!!",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
