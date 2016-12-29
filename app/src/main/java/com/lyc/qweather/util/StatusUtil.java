package com.lyc.qweather.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * Created by wayne on 2016/12/29.
 */

public class StatusUtil {

    public static void setStatusColor(Activity activity,int color){
        if(Build.VERSION.SDK_INT>=21){
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            activity.getWindow().setStatusBarColor(color);
        }
    }
}
