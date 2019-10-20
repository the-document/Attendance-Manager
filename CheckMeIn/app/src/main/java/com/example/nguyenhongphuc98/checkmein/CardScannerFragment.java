package com.example.nguyenhongphuc98.checkmein;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CardScannerFragment extends Fragment {

    private static Camera cameraInstance;
    private Camera mCamera;
    private CameraPreview mPreview;
    FrameLayout previewLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_card_scanner, container, false);
        mCamera = getCameraInstance(mPreview);
        mPreview = new CameraPreview(view.getContext(), mCamera);

        //Cài đặt preview camera.
        previewLayout = (FrameLayout)view.findViewById(R.id.fv_camera_preview);
        previewLayout.addView(mPreview);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCamera.startPreview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.stopPreview();
        releaseCameraInstance();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    private boolean checkCameraHardwareAvailability (Context context)
    {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        else
            return false;
    }

    private static Camera getCameraInstance(CameraPreview camPreview){
        try
        {
            if (cameraInstance == null) {
                cameraInstance = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cameraInstance;
    }

    private static void releaseCameraInstance()
    {
        if (cameraInstance != null)
        {
            cameraInstance.release();
            cameraInstance = null;
        }
    }
}