package com.example.nguyenhongphuc98.checkmein.UI.scan;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyenhongphuc98.checkmein.R;

public class CardScannerFragment extends Fragment {

    private CameraPreview mPreview;
    FrameLayout previewLayout;
    ConstraintLayout layout;
    ImageView imgViewCardScanningBarLeft;
    ImageView imgViewCardScanningBarRight;
    ImageView imgViewScanPreview;
    TextView txtViewScannedText;

    //Callback để nhận ảnh Preview trả về từ Camera.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_card_scanner, container, false);

        //Ánh xạ.
        layout = (ConstraintLayout)view.findViewById(R.id.fragment_card_scanner_layout);
        imgViewCardScanningBarLeft = (ImageView)view.findViewById(R.id.fragment_card_scanner_scanning_bar);
        imgViewCardScanningBarRight = (ImageView)view.findViewById(R.id.fragment_card_scanner_scanning_bar_2);
        imgViewScanPreview = (ImageView)view.findViewById(R.id.fragment_card_scanner_scan_preview);
        txtViewScannedText = (TextView)view.findViewById(R.id.fragment_card_scanner_scanned_text);

        mPreview = new CameraPreview(view.getContext(), imgViewScanPreview, txtViewScannedText);

        //Cài đặt preview camera.
        previewLayout = view.findViewById(R.id.fv_camera_preview);

        //Animation cho thanh chạy.
        final Animation scanningAnimationBottomToTop = AnimationUtils.loadAnimation(getContext(), R.anim.custom_scanning_moving_bar_bottom_to_top);
        final Animation scanningAnimationTopToBottom = AnimationUtils.loadAnimation(getContext(), R.anim.custom_scanning_moving_bar_top_to_bottom);

        //Chạy animation.
        imgViewCardScanningBarLeft.startAnimation(scanningAnimationBottomToTop);
        imgViewCardScanningBarRight.startAnimation(scanningAnimationTopToBottom);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mCamera != null)
//            mCamera.startPreview();
        previewLayout.addView(mPreview);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mCamera.stopPreview();
        previewLayout.removeView(mPreview);
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (mCamera != null)
//            mCamera.stopPreview();
        previewLayout.removeView(mPreview);
    }

    private boolean checkCameraHardwareAvailability (Context context)
    {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        else
            return false;
    }


}
