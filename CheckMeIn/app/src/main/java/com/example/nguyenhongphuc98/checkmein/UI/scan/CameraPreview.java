package com.example.nguyenhongphuc98.checkmein.UI.scan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.example.nguyenhongphuc98.checkmein.Utils.Tesseract;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback, Camera.PreviewCallback {

    //Biến này dùng để quản lý marginTop và marginRight của khung dùng để cắt MSSV (theo phần trăm).
    //VD : Nếu giá trị = 0.2 thì tức là chỉ lấy 20% độ cao màn hình.
    final double percentHeightMSSV = 0.2;
    final double percentWidthMSSV = 0.2;

    //Kích cỡ của Focus Area.
    final float focusAreaSize = 72;

    //Delay để lấy một frame.
    final double frameCaptureDelay = 3;

    //Tính thời gian đã qua.
    double timeHolder = 0;

    //Giữ frame hiện tại, để chúng ta có thể thao tác với frame hiện tại.
    private final Lock lock = new ReentrantLock();
    private byte[] mPreviewFrameBuffer;

    private SurfaceHolder mHolder;

    private static Camera cameraInstance;

    private Camera mCamera;
    private boolean meteringAreaSupported = false;

    Tesseract tesseract;

    Context context;

    public CameraPreview(Context context) {
        super(context);
        this.context = context;

        //Ánh xạ.
        tesseract = new Tesseract(context, "eng");

        Log.d("onCreateCameraPreview","Camera Preview Created !");

        mHolder = getHolder();

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
            mCamera = getCameraInstance(this);
            //Chỉnh các cài đặt cho Camera.
            setupCamera();
            mCamera.setPreviewCallback(this);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }
        catch(IOException ex){
        }
    }

    void addCallbackToView(SurfaceHolder.Callback callback){
        mHolder.addCallback(callback);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null || mCamera == null)
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
        releaseCameraInstance();
        mCamera.setPreviewCallback(null);
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {

    }

    public Bitmap convertYuvByteArrayToBitmap(byte[] data, Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = parameters.getPreviewSize();
        YuvImage image = new YuvImage(data, parameters.getPreviewFormat(), size.width, size.height, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, out);
        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}
