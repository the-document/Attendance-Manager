package com.example.nguyenhongphuc98.checkmein.UI.scan;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback {

    //Kích cỡ của Focus Area.
    final float focusAreaSize = 72;

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private boolean meteringAreaSupported = false;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        Log.d("onCreateCameraPreview","Camera Preview Created !");

        //Chỉnh các cài đặt cho Camera.
        setupCamera();

        mHolder = getHolder();
        mHolder.addCallback(this);

        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //Chỉnh cài đặt cho sự kiện chạm vào màn hình để lấy nét.
        OnTouchListener touchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("onTouchCameraPreview", "Camera Preview Touched !");
                focusOnTouch(event);
                return false;
            }
        };
        this.setOnTouchListener(touchListener);
    }

    //Hàm này sẽ được gọi khi người dùng chạm tay vào màn hình để lấy nét.
    protected void focusOnTouch (MotionEvent event){
        if (mCamera != null){
            Log.d("focusOnTouch", "Success !");
            mCamera.cancelAutoFocus();
            Rect focusRect = getTapArea(event.getX(), event.getY(), 1f);
            Rect meteringRect = getTapArea(event.getX(), event.getY(), 1.5f);

            //Danh sách những vùng sẽ được Camera focus.
            List focusArea = new ArrayList<Camera.Area>();
            focusArea.add(new Camera.Area(focusRect, 1000));
            List meteringArea = new ArrayList<Camera.Area>();
            meteringArea.add(new Camera.Area(meteringRect,1000));

            //Chỉnh lại chế độ focus của camera.
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

            //Cài đặt vùng focus của Camera.
            parameters.setFocusAreas(focusArea);

            if (meteringAreaSupported){
                parameters.setMeteringAreas(meteringArea);
            }

            mCamera.setParameters(parameters);
            mCamera.autoFocus(this);
        }
        else{
            Log.d("focusOnTouch", "Camera is null");
        }
    }

    private void setupCamera(){
        Camera.Parameters parameters = mCamera.getParameters();
        //Kiểm tra xem camera hiện tại có hỗ trợ chế độ Auto Focus không.
        if (parameters.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        mCamera.setParameters(parameters);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }
        catch(IOException ex){
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null)
        {
            //Surface không tồn tại.
            return;
        }
        try {
            mCamera.stopPreview();
        }
        catch (Exception ex){

        }
        try {
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }
        catch (Exception ex)
        {

        }

        //Kiểm tra xem Metering Area có được hỗ trợ không.
        Camera.Parameters p = mCamera.getParameters();
        if (p.getMaxNumMeteringAreas() > 0){
            this.meteringAreaSupported = true;
        }
    }

    private Rect getTapArea(float x, float y, float coefficent){
        int areaSize = Float.valueOf(focusAreaSize * coefficent).intValue();

        int left = getValueInBoundary((int)x - areaSize/2, 0, getWidth() - areaSize);
        int top = getValueInBoundary((int)x - areaSize/2, 0, getHeight() - areaSize);

        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    //Trả về giá trị nằm trong khoảng màn hình (phòng trường hợp người dùng bấm ở rìa màn hình).
    private int getValueInBoundary(int x, int min, int max){
        if (x > max){
            return max;
        }
        if (x < min){
            return min;
        }
        return x;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {

    }
}
