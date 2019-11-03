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

public class EventAdapter extends ArrayAdapter {
    Context context;

    public EventAdapter(@NonNull Context context,String temp[]) {
        super(context, R.layout.custom_row_event,temp);
        this.context=context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder") View row=layoutInflater.inflate(R.layout.custom_row_event,parent,false);
        CircularImageView img=row.findViewById(R.id.avtRowEvent);
        TextView TitleEvent=row.findViewById(R.id.nameRowEvent);
        TextView DateEvent=row.findViewById(R.id.dateRowEvent);
        View rowEvent=row.findViewById(R.id.rowEvent);

        //change color if this is event just register and not take part in
        if(position==3)
            rowEvent.setBackgroundResource(R.drawable.custom_row_event_orange);

        img.setImageResource(R.drawable.ninja_avt);
        TitleEvent.setText("Training CSDL 2019");
        DateEvent.setText("22/11/1998");
        return row;
    }
}
