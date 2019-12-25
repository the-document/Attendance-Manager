package com.example.nguyenhongphuc98.checkmein.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.nguyenhongphuc98.checkmein.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class CollaborationAdapter extends BaseAdapter {

    List<String> lsImv;
    Context context;

    public CollaborationAdapter(List<String> lsImg, Context context) {
        this.lsImv = lsImg;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lsImv.size();
    }

    @Override
    public Object getItem(int position) {
        return lsImv.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CircularImageView avt=new CircularImageView(context);

        if(lsImv.size()<=position|| lsImv.get(position).equals("null"))
            return null;

        //ImageView t=new ImageView(context);
        Glide.with(context)
                .load(Uri.parse(lsImv.get(position)))
                .into(avt);

        avt.setLayoutParams(new GridView.LayoutParams(60,60));

        return avt;

    }
}
