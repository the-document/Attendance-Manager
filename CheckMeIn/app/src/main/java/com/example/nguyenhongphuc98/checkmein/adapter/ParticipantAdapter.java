package com.example.nguyenhongphuc98.checkmein.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ParticipantAdapter extends ArrayAdapter {
    Context context;

    public ParticipantAdapter(@NonNull Context context, String temp[]) {
        super(context, R.layout.custom_row_participant,temp);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder") View row=layoutInflater.inflate(R.layout.custom_row_participant,parent,false);
        CircularImageView img=row.findViewById(R.id.avtRowParticipant);
        TextView TitleEvent=row.findViewById(R.id.tvInfoParticipant);

        img.setImageResource(R.drawable.ninja_avt);
        TitleEvent.setText("Nguyễn Cao Luyện - 16520713");
        return row;
    }
}
