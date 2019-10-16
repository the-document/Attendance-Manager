package com.example.nguyenhongphuc98.checkmein.OCR;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Tesseract {
    private final TessBaseAPI mTesseract;

    String mPathSaveData="/tesseract/tessdata/";
    String mPathToInitTesseract;
    String mPathFileSave;
    String mTrainedDataNamel="eng.traineddata";

    public Tesseract(Context context,String language) {

        mTesseract=new TessBaseAPI();
        AssetManager assetManager=context.getAssets();
        InputStream inputFile=null;
        FileOutputStream outputFile=null;

        //add pre root dir
        mPathSaveData=context.getFilesDir()+mPathSaveData;
        mPathToInitTesseract=context.getFilesDir()+"/tesseract";
        mPathFileSave=mPathSaveData+mTrainedDataNamel;

        //start transfer data from asset to dir
        Boolean isCreated=false;
        try {
            //open source data
            inputFile=assetManager.open(mTrainedDataNamel);

            //create foler to save data
            File fileToSave=new File(mPathSaveData);

            if(fileToSave.exists()==false){
                if(fileToSave.mkdir()==false){
                    Toast.makeText(context, "Can't init data from OCR", Toast.LENGTH_SHORT).show();
                }

                //create stream file to save
                outputFile=new FileOutputStream(new File(mPathFileSave));
            }
            else {
                isCreated=true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            //if file already create and transfer data
            if(isCreated){
                if(inputFile!=null) {
                    try {
                        inputFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mTesseract.init(mPathToInitTesseract,language);
                return;
            }

            if (inputFile != null && outputFile != null) {
                try {
                    //copy file
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputFile.read(buf)) != -1) {
                        outputFile.write(buf, 0, len);
                    }
                    inputFile.close();
                    outputFile.close();
                    mTesseract.init(mPathToInitTesseract, language);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Toast.makeText(context, "Can't transfer data OCR", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public String getOCRResult(Bitmap bitmap) {
        mTesseract.setImage(bitmap);
        return mTesseract.getUTF8Text();
    }


    public void onDestroy() {
        if (mTesseract != null) mTesseract.end();
    }
}
