package com.example.nguyenhongphuc98.checkmein;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.adapter.PageAdapterListParticipant;
import com.example.nguyenhongphuc98.checkmein.adapter.ParticipantAdapter;

public class ParticipantListFragment extends Fragment {
    ParticipantAdapter adapter;
    ListView lv_participant_list;
    String temp1[]={"a","b","c","d"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_participant, container, false);
        lv_participant_list = (ListView) view.findViewById(R.id.lv_participant_list);
        adapter=new ParticipantAdapter(getContext(),temp1);
        lv_participant_list.setAdapter(adapter);
        return view;
    }
}
