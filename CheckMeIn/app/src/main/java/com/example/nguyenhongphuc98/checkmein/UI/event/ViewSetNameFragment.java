package com.example.nguyenhongphuc98.checkmein.UI.event;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewSetNameFragment extends Fragment {

    Button btnNextToTime;
    EditText etName;
    EditText etCode;

    public ViewSetNameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_view_set_name, container, false);

        btnNextToTime=view.findViewById(R.id.btnNextToTime);
        etName=view.findViewById(R.id.etEventName);
        etCode=view.findViewById(R.id.etEventCode);

        btnNextToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataCenter.eventName=etName.getText().toString();
                DataCenter.eventCode=etCode.getText().toString();

                ViewPager pager = (ViewPager) view.getParent();
                pager.setCurrentItem(2);
            }
        });

        return view;
    }

}
