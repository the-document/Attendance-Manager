package com.example.nguyenhongphuc98.checkmein;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.nguyenhongphuc98.checkmein.adapter.ParticipantAdapter;


public class ParticipantListFragment extends Fragment {
    ParticipantAdapter adapter;
    ListView lv_participant_list;
    String temp1[]={"a","b","c","d"};
    Button btnExtend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_participant, container, false);

        lv_participant_list = view.findViewById(R.id.lv_participant_list);
        btnExtend = view.findViewById(R.id.button);

        adapter=new ParticipantAdapter(getContext(),temp1);
        lv_participant_list.setAdapter(adapter);

        btnExtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ExtendDialogFragment dtDialog = new ExtendDialogFragment();
                dtDialog.show(getFragmentManager(), "dialog");
            }
        });

        return view;
    }
}
