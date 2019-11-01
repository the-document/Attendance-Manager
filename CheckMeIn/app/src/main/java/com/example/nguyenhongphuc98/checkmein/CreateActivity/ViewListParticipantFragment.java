package com.example.nguyenhongphuc98.checkmein.CreateActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.Adapter.ParticipantAdapter;
import com.example.nguyenhongphuc98.checkmein.R;

public class ViewListParticipantFragment extends Fragment {
    ListView listView;
    ParticipantAdapter adapter;
    String temp1[]={"a","b","c","d"};

    public ViewListParticipantFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_view_list_participant, container, false);
        listView=view.findViewById(R.id.lvParticipants);
        adapter=new ParticipantAdapter(getContext(),temp1);
        listView.setAdapter(adapter);

        return view;
    }
}
