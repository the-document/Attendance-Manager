package com.example.nguyenhongphuc98.checkmein.UI.scan;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.example.nguyenhongphuc98.checkmein.Utils.AppExecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CardScannerFragment extends Fragment {

    private CameraPreview mPreview;
    FrameLayout previewLayout;
    ConstraintLayout layout;
    ImageView imgViewCardScanningBarLeft;
    ImageView imgViewCardScanningBarRight;
    ImageView imgViewCardScanningAnimation;
    ImageView imgViewScanPreview;
    ImageView imgViewMSSVCaptureBox;
    ImageView imgViewScanMSSVPreview;
    TextView txtViewScannedText;

    //Lock để khoá không cho phép chạy 2 lần việc xử lý hình ảnh.
    Lock imageProcessingLock = new ReentrantLock();

    //Đây là biến dùng để lưu trữ MSSV sau khi nhận diện thẻ thành công.
    Integer scannedMSSVNumber = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_card_scanner, container, false);

        //Ánh xạ.
        layout = (ConstraintLayout)view.findViewById(R.id.fragment_card_scanner_layout);
        imgViewCardScanningBarLeft = (ImageView)view.findViewById(R.id.fragment_card_scanner_scanning_bar);
        imgViewCardScanningBarRight = (ImageView)view.findViewById(R.id.fragment_card_scanner_scanning_bar_2);
        imgViewScanPreview = (ImageView)view.findViewById(R.id.fragment_card_scanner_scan_preview);
        imgViewScanMSSVPreview = (ImageView)view.findViewById(R.id.fragment_card_scanner_MSSV_preview);
        imgViewMSSVCaptureBox = (ImageView)view.findViewById(R.id.fragment_card_scanner_MSSV_imgView);
        imgViewCardScanningAnimation = (ImageView)view.findViewById(R.id.fragment_card_scanner_animation);
        txtViewScannedText = (TextView)view.findViewById(R.id.fragment_card_scanner_scanned_text);
        //Cài đặt preview camera.
        previewLayout = view.findViewById(R.id.fv_camera_preview);

        mPreview = new CameraPreview(view.getContext()){
            @Override
            public void onPreviewFrame(byte[] data, Camera camera){

                if (System.currentTimeMillis()/1000 - timeHolder < frameCaptureDelay)
                    return;
                txtViewScannedText.setText("Processing ... ");
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        boolean isLockAquired = imageProcessingLock.tryLock();
                        if (!isLockAquired)
                            return;
                        Bitmap capturedImage = convertYuvByteArrayToBitmap(data,camera);
                        int ciHeight = capturedImage.getHeight();
                        int ciWidth = capturedImage.getWidth();

                        //location[0] là X, location[1] là Y.
                        int[] location = new int[2];
                        imgViewMSSVCaptureBox.getLocationOnScreen(location);
                        final int scanningAnimPosX = location[0];
                        final int scanningAnimPosY = location[1];

                        //Do hình ảnh cắt ra bị ngược nên ta phải đảo lại.
                        int srcX = scanningAnimPosY + 60;
                        int srcY = (int)((1-percentHeightMSSV)*ciHeight) - scanningAnimPosX + 25;

//                Bitmap resizedBitmap = Bitmap.createBitmap(capturedImage, srcX, srcY,
//                        (int)(percentWidthMSSV * ciWidth), (int)(percentHeightMSSV * ciHeight));

                        final int captureBoxWidth = imgViewMSSVCaptureBox.getMeasuredWidth();
                        final int captureBoxHeight = imgViewMSSVCaptureBox.getMeasuredHeight();

                        Bitmap resizedBitmap = Bitmap.createBitmap(capturedImage, srcX, srcY,
                                captureBoxHeight + 60, captureBoxWidth + 25);
                        String scannedText = tesseract.getOCRResult(resizedBitmap);

                        try {
                            scannedMSSVNumber = Integer.parseInt(scannedText);
                        }catch(NullPointerException nptrEx){
                            scannedMSSVNumber = -1;
                        }
                        catch(NumberFormatException ex){
                            scannedMSSVNumber = -1;
                        }

                        String tmp = "";
                        if (scannedMSSVNumber == -1){
                            tmp = "Try again : " + scannedText;
                        }
                        else{
                            tmp = scannedMSSVNumber.toString();
                        }

                        final String scanResult = tmp;

                        AppExecutors.getInstance().mainThread().execute(() -> {
                            imgViewScanPreview.setImageBitmap(capturedImage);
                            imgViewScanMSSVPreview.setImageBitmap(resizedBitmap);
                            txtViewScannedText.setText(scanResult);
                            timeHolder = System.currentTimeMillis()/1000;
                        });
                        imageProcessingLock.unlock();
                    }
                });
            }
        };

        mPreview.addCallbackToView(mPreview);

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
