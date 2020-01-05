package com.example.nguyenhongphuc98.checkmein.OthersActivity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.nguyenhongphuc98.checkmein.R;

public class LoadingDialog {

    Activity activity;
    Dialog dialog;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }

    public void showDialog()
    {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Không cho người dùng tự tắt dialog.
        dialog.setCancelable(false);

        //Cài đặt loading view.
        dialog.setContentView(R.layout.custom_loading_layout);

        //Cài đặt tuỳ chọn transparent.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Hiển thị dialog lên sau khi cấu hình mọi thứ xong.
        dialog.show();
    }

    public void hideDialog(){
        dialog.dismiss();
    }

}
