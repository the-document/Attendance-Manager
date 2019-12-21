package com.example.nguyenhongphuc98.checkmein;

import android.app.Application;
import android.content.Context;

public class CheckMeIn extends Application {
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        CheckMeIn.context = getApplicationContext();
    }

    public static Context getAppContext(){
        return CheckMeIn.context;
    }
}
