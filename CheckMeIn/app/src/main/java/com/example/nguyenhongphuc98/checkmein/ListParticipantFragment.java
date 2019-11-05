package com.example.nguyenhongphuc98.checkmein;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyenhongphuc98.checkmein.adapter.PageAdapterListParticipant;

public class ListParticipantFragment extends Fragment {
    ViewPager participantViewPager;
    PageAdapterListParticipant pageAdapterListParticipant;

    public ListParticipantFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_participant, container, false);
        participantViewPager=view.findViewById(R.id.vpParticipant);
        pageAdapterListParticipant=new PageAdapterListParticipant(getFragmentManager(),1);
        participantViewPager.setAdapter(pageAdapterListParticipant);
        participantViewPager.setCurrentItem(0);
        return view;
    }


}
