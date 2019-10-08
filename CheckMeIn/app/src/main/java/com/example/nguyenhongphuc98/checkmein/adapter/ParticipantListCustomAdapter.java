package com.example.nguyenhongphuc98.checkmein.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.model.Participant;

import java.util.ArrayList;
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
