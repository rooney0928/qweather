package com.lyc.qweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.lyc.qweather.activity.WeatherActivity;
import com.lyc.qweather.base.BaseApplication;
import com.lyc.qweather.gson.Weather;
import com.lyc.qweather.util.HttpUtil;
import com.lyc.qweather.util.LogUtil;
import com.lyc.qweather.util.PrefUtils;
import com.lyc.qweather.util.ToastUtil;
import com.lyc.qweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wayne on 2016/12/29.
 */

public class AutoUpdateService extends Service {

    private static int UPDATE_HOURS = 6;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingPic();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = UPDATE_HOURS * 60 * 60 * 1000;//8小时毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this,AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);

        LogUtil.d("qservice","start");
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        String weatherStr = PrefUtils.getString(this, "weather", null);
        if (weatherStr != null) {
            Weather weather = Utility.handleWeatherResponse(weatherStr);
            String weatherId = weather.basic.weatherId;
            String url = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=" + BaseApplication.HE_KEY;
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    PrefUtils.putString(AutoUpdateService.this, "weather", responseText);
                }
            });
        }

    }

    private void updateBingPic() {
        String url = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                PrefUtils.putString(AutoUpdateService.this, "bing_pic", bingPic);
            }
        });
    }
}
