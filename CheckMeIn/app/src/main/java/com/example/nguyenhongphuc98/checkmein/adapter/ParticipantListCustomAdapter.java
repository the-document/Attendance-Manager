package com.example.nguyenhongphuc98.checkmein.adapter;

import android.content.Context;
import androidx.annotation.NonNull;

import android.widget.ArrayAdapter;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Participant;

import java.util.List;

public class ParticipantListCustomAdapter extends ArrayAdapter<Participant> {
    private Context context;
    private int resources;
    private List<Participant> participantArrayList;


    public ParticipantListCustomAdapter(@NonNull Context context, int resource, @NonNull List<Participant> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resources = resource;
        this.participantArrayList = objects;
    }

    @Override
    public int getCount() {
        return participantArrayList.size();
    }



}
