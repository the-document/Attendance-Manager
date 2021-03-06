package com.example.nguyenhongphuc98.checkmein.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.nguyenhongphuc98.checkmein.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;
import java.util.List;

public class OrganAdaptor extends BaseAdapter {
    HashMap<String,String> lsImageView;
    List<String> lsOrganID;
    Context context;

    public OrganAdaptor(HashMap<String,String> lsimg,List<String>_lsOrganID, Context context) {
        this.lsImageView = lsimg;
        this.lsOrganID=_lsOrganID;
        this.context = context;
    }

    public void SetOrganAdaptor(HashMap<String,String> lsimg,List<String>_lsOrganID) {
        this.lsImageView = lsimg;
        this.lsOrganID=_lsOrganID;
    }

    @Override
    public int getCount() {
        return lsOrganID.size();
    }

    @Override
    public Object getItem(int position) {
        return lsOrganID.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewRow= convertView;

        if(viewRow==null){
            viewRow=layoutInflater.inflate(R.layout.custom_button_organ,parent,false);
            OrganAdaptor.ViewHolder holder=new OrganAdaptor.ViewHolder();
            holder.img =viewRow.findViewById(R.id.iv_avatar_organ);

            viewRow.setTag(holder);
        }

        OrganAdaptor.ViewHolder viewHolder= (OrganAdaptor.ViewHolder) viewRow.getTag();


        String key=lsOrganID.get(position);
        if(key==null||key.equals("null"))
            return viewRow;

        //ImageView t=new ImageView(context);
        try
        {
            Glide.with(context)
                    .load(Uri.parse(lsImageView.get(lsOrganID.get(position))))
                    .into(viewHolder.img);
        }
       catch (Exception e){
           Log.e("DTM","err load img:"+e.getMessage());
           viewHolder.img.setImageResource(R.drawable.avatar);
       }

        Log.e("DTM","got view organ:"+position);
        return viewRow;

    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        Log.e("DTM","notify data change");
    }

    public void DeleteOrgan(String organID){
        for (String organ : lsOrganID) {
            if(organ.equals(organID))
            {
                lsOrganID.remove(organ);
                Log.e("DTM","remove:" + organ);
                break;
            }

        }

        lsImageView.remove(organID);
        notifyDataSetChanged();
    }

    public static class ViewHolder{
        ImageView img;
    }
}
