package com.example.nguyenhongphuc98.checkmein.OCR;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class PreProcess {

//    Bitmap testdata= BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.horizontal_card);
//    Bitmap out=PreProcess.GetInstance().GetRoi(testdata);
//
//
//    //Bitmap input=Bitmap.createScaledBitmap(testdata,806,604,true);
//                iv.setImageBitmap(out);
//    Tesseract tesseract=new Tesseract(getApplicationContext(),"eng");
//    String r=tesseract.getOCRResult(out);
//                Toast.makeText(getApplicationContext(),r,Toast.LENGTH_SHORT).show();

    private static PreProcess instance;

    public static PreProcess GetInstance(){
        if(instance==null)
            instance=new PreProcess();
        return  instance;
    }

    private PreProcess(){

    }

    public Bitmap GetRoi(Bitmap rawData){
       Bitmap input=Bitmap.createScaledBitmap(rawData,806,604,true);

        Mat src=new Mat();
        Mat des=new Mat();
        Mat blur=new Mat();
        Mat threshold=new Mat();

        //Bitmap roi;

        Utils.bitmapToMat(input, src);

        //convert to gray
        Imgproc.cvtColor(src,des,Imgproc.COLOR_RGB2GRAY);

        //blur and threshold
        Imgproc.GaussianBlur(des,blur,new Size(3,3),0);
        Imgproc.threshold(blur,threshold,120,255,Imgproc.THRESH_BINARY);

        //find countour
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(threshold, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        double maxVal = 0;
        int maxValIdx = 0;
        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++)
        {
            double contourArea = Imgproc.contourArea(contours.get(contourIdx));
            if (maxVal < contourArea)
            {
                maxVal = contourArea;
                maxValIdx = contourIdx;
            }
        }

        //Imgproc.drawContours(src, contours, maxValIdx, new Scalar(0,255,0), 5);

        RotatedRect rect =Imgproc.minAreaRect(new MatOfPoint2f(contours.get(maxValIdx).toArray()));


        Mat CardArea = GetCardArea(des,rect);

        Mat IDArea=GetIDArea(CardArea);

        Bitmap outbmp=Bitmap.createBitmap(IDArea.width(),IDArea.height(), Bitmap.Config.ARGB_8888);
        //Bitmap outbmp=Bitmap.createBitmap(CardArea.width(),CardArea.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(IDArea,outbmp);
        return outbmp;
    }

    private Mat GetCardArea(Mat src, RotatedRect rect){

        int height=(int)rect.size.height;
        int width=(int)rect.size.width;

        Mat rotation_mat = Imgproc.getRotationMatrix2D(rect.center,rect.angle,1.0);
        double abs_cos = abs((float)rotation_mat.get(0,0)[0]);
        double abs_sin = abs((float)rotation_mat.get(0,1)[0]);
        Log.d("INFOR","rotate00"+rotation_mat.get(0,0)[0]);
        Log.d("INFOR","rotate01"+rotation_mat.get(0,1)[0]);

        int bound_w = (int)(height * abs_sin + width * abs_cos)+80;
        int bound_h = (int)(height * abs_cos + width * abs_sin);

        //subtract old image center (bringing image back to origo) and adding the new image center coordinates
        //rotation_mat[0, 2] += bound_w/2 - center[0]
        //rotation_mat[1, 2] += bound_h/2 - center[1]

        rotation_mat.put(0,2,rotation_mat.get(0,2)[0]+(bound_w/2 - rect.center.x));
        rotation_mat.put(1,2,rotation_mat.get(1,2)[0]+(bound_h/2 - rect.center.y));

        //rotate image with the new bounds and translated rotation matrix
        Mat rotated_mat =new Mat();
        Imgproc.warpAffine(src,rotated_mat ,rotation_mat,new Size(bound_w, bound_h));
        Mat out=new Mat();
        Imgproc.getRectSubPix(rotated_mat, rect.size, new Point(bound_w/2,bound_h/2),out);
        return out;
    }

    private Mat GetIDArea(Mat input){
        Mat rotated=input.clone();
        if(input.size().height>input.size().width){
            Core.rotate(input,rotated,Core.ROTATE_90_CLOCKWISE);
        }

        int t1=(int)(rotated.height()*0.85);
        int l2=(int)(rotated.width()*0.65);
        int w=(int)(rotated.width()*0.35);
        int h=(int)(rotated.height()*0.15);

        Mat m1=new Mat();
        m1=rotated.submat(new Rect(0,t1,w,h));
        Mat m2=new Mat();
        m2=rotated.submat(new Rect(l2,0,w,h));

        int c1=0,c2=0;
        for(int i=0;i<m1.height();i++){
            for (int j=0;j<m1.width();j++){
                if(m1.get(i,j)[0]>120)
                    c1++;
                if(m2.get(i,j)[0]>120)
                    c2++;
            }
        }

        if(c1>c2)
            return  m1;
        else
            return m2;
    }
}
