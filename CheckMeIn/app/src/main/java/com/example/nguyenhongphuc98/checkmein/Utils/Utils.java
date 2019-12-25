package com.example.nguyenhongphuc98.checkmein.Utils;

import android.content.res.Resources;
import android.util.TypedValue;

import com.example.nguyenhongphuc98.checkmein.CheckMeIn;

public class Utils {
    public static int fromDPtoPX(int dp){
        Resources r = CheckMeIn.getAppContext().getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }
}
