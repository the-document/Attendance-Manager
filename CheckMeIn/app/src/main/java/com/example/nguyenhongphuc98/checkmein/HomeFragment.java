package com.example.nguyenhongphuc98.checkmein;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.Animate.ResizeWidthAnimation;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ListActivityFragment lsActivityFragment;
    OrganFragment organFragment;


    View ctnCheckIn;
    ImageView avtCheckIn;
    EditText etActivityCode;
    Button btnCreateOrgan;

    private List<ImageButton> mListOrganization;
    private RelativeLayout mOrganizationContainer;

    public HomeFragment() {
        // Required empty public constructor
        mListOrganization=new ArrayList<>();
        lsActivityFragment=new ListActivityFragment();
        organFragment=new OrganFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);


        mOrganizationContainer=(RelativeLayout) view.findViewById(R.id.organization_container);
        MatchView(view);

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



        //add event click checkin
        ctnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avtCheckIn.setVisibility(View.INVISIBLE);
                etActivityCode.setVisibility(View.VISIBLE);

                ResizeWidthAnimation anim = new ResizeWidthAnimation(etActivityCode, 350);
                anim.setDuration(400);
                etActivityCode.startAnimation(anim);
            }
        });

        etActivityCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etActivityCode.getRight() - etActivityCode.getCompoundDrawables()[2].getBounds().width())) {
                        Toast.makeText(view.getContext(),"Joining",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });


        btnCreateOrgan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransition=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransition.replace(R.id.fragment_container,organFragment);
                fragmentTransition.commit();
            }
        });

        return  view;
    }

    private void MatchView(View view){
        ctnCheckIn=view.findViewById(R.id.ctnCheckIn);
        avtCheckIn=view.findViewById(R.id.avtCheckIn);
        etActivityCode=view.findViewById(R.id.etActivityCode);
        btnCreateOrgan=view.findViewById(R.id.btnCreateOrgan);
    }

}
