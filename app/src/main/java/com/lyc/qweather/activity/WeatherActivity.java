package com.lyc.qweather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lyc.qweather.R;
import com.lyc.qweather.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by wayne on 2016/12/29.
 */

public class WeatherActivity extends BaseActivity{
    @BindView(R.id.sv_weather_layout)
    ScrollView sv_weather_layout;

    @BindView(R.id.tv_city_title)
    TextView tv_city_title;
    @BindView(R.id.tv_update_time)
    TextView tv_update_time;

    @BindView(R.id.tv_degree)
    TextView tv_degree;
    @BindView(R.id.tv_weather_info)
    TextView tv_weather_info;

    @BindView(R.id.ll_forecast_layout)
    LinearLayout ll_forecast_layout;

    @BindView(R.id.tv_aqi)
    TextView tv_aqi;
    @BindView(R.id.tv_pm25)
    TextView tv_pm25;

    @BindView(R.id.tv_comfort)
    TextView tv_comfort;
    @BindView(R.id.tv_car_wash)
    TextView tv_car_wash;
    @BindView(R.id.tv_sport)
    TextView tv_sport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }
}
