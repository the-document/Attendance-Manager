package com.example.nguyenhongphuc98.checkmein;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ListActivityFragment lsActivityFragment;

    private List<ImageButton> mListOrganization;
    private RelativeLayout mOrganizationContainer;

    public HomeFragment() {
        // Required empty public constructor
        mListOrganization=new ArrayList<>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lsActivityFragment=new ListActivityFragment();

        mOrganizationContainer=(RelativeLayout) view.findViewById(R.id.organization_container);

        //load from DB and add to this list for current user
//        int sizeOfList=5;
//        for(int i=0;i<sizeOfList;i++){
//            ImageButton btn=new ImageButton(getContext());
//            btn.setBackgroundResource(R.drawable.custom_button_organization);
//            btn.setImageResource(R.drawable.icon_home);
//        }

        ImageButton btn=new ImageButton(getContext());
        btn.setBackgroundResource(R.drawable.custom_button_organization);
        btn.setImageResource(R.drawable.icon_home);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20,20,20,20);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransition=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransition.replace(R.id.fragment_container,lsActivityFragment);
                fragmentTransition.commit();
            }
        });
        mOrganizationContainer.addView(btn,params);

        return  view;
    }

}
