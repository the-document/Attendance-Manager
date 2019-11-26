package com.example.nguyenhongphuc98.checkmein.UI.scan;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.scan.CameraPreview;
import com.tbruyelle.rxpermissions2.RxPermissions;

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
        previewLayout = view.findViewById(R.id.fv_camera_preview);
        previewLayout.addView(mPreview);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCamera != null)
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
        if (mCamera != null)
            mCamera.stopPreview();
    }

    private boolean checkCameraHardwareAvailability (Context context)
    {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        else
            return false;
    }

    private Camera getCameraInstance(CameraPreview camPreview){
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
