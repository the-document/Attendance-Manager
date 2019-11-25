package com.example.nguyenhongphuc98.checkmein.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        View viewRow= convertView;

        if(viewRow==null){
            viewRow=layoutInflater.inflate(R.layout.custom_row_event,parent,false);
            ViewHolder holder=new ViewHolder();
            holder.imageView =viewRow.findViewById(R.id.avtRowEvent);
            holder.title =viewRow.findViewById(R.id.nameRowEvent);
            holder.date =viewRow.findViewById(R.id.dateRowEvent);
            holder.border =viewRow.findViewById(R.id.rowEvent);

            viewRow.setTag(holder);
        }

        ViewHolder viewHolder= (ViewHolder) viewRow.getTag();

        //change color if this is event just register and not take part in
        if(position==3)
            viewHolder.border.setBackgroundResource(R.drawable.custom_row_event_orange);

        viewHolder.imageView.setImageResource(R.drawable.ninja_avt);
        viewHolder.title.setText("Training CSDL 2019");
        viewHolder.date.setText("22/11/1998");

        return viewRow;
    }

    public static class ViewHolder{
        View border;
        CircularImageView imageView;
        TextView title;
        TextView date;
    }
}
