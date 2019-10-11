package com.example.nguyenhongphuc98.checkmein.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.nguyenhongphuc98.checkmein.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class CollaborationAdapter extends BaseAdapter {

    List<String> lsUrlImage;
    Context context;

    public CollaborationAdapter(List<String> lsUrlImage, Context context) {
        this.lsUrlImage = lsUrlImage;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lsUrlImage.size();
    }

    @Override
    public Object getItem(int position) {
        return lsUrlImage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CircularImageView avt=new CircularImageView(context);

        //set background of lvImage[position]
        avt.setBackgroundResource(R.drawable.avatar);
        avt.setLayoutParams(new GridView.LayoutParams(41,41));
        return avt;
    }
}
