package com.lyc.qweather.base;

import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by wayne on 2016/12/27.
 */

public class BaseApplication extends LitePalApplication{
    public static Context context;

    /**
     * 和风天气key
     */
    public static final String HE_KEY = "1b8f15eead4c403b9208e7a514d58517";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
