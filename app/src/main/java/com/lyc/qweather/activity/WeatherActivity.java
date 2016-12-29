package com.lyc.qweather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyc.qweather.R;
import com.lyc.qweather.base.BaseActivity;
import com.lyc.qweather.gson.Forecast;
import com.lyc.qweather.gson.Weather;
import com.lyc.qweather.util.HttpUtil;
import com.lyc.qweather.util.PrefUtils;
import com.lyc.qweather.util.ToastUtil;
import com.lyc.qweather.util.Utility;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wayne on 2016/12/29.
 */

public class WeatherActivity extends BaseActivity {
    @BindView(R.id.iv_bing_img)
    ImageView iv_bing_img;
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
        ButterKnife.bind(this);

        String bingPic = PrefUtils.getString(this,"bing_pic",null);
        if(bingPic!=null){
            Glide.with(this).load(bingPic).centerCrop().into(iv_bing_img);
        }else{
            loadBingPic();
        }

        String weatherStr = PrefUtils.getString(this, "weather", null);
        if (weatherStr != null) {
            //有缓存直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherStr);
            showWeatherInfo(weather);
        }else{
            //无缓存时去服务器查询天气
            String weatherId = getIntent().getStringExtra("weather_id");
            sv_weather_layout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }


    }

    private void loadBingPic() {
        String url = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                PrefUtils.putString(WeatherActivity.this,"bing_pic",bingPic);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).centerCrop().into(iv_bing_img);
                    }
                });
            }
        });
    }

    private void requestWeather(String weatherId) {
        String url = "http://guolin.tech/api/weather?cityid="+ weatherId+"&key=" + HE_KEY;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showSimpleToast(WeatherActivity.this,"获取天气信息失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather!=null &&"ok".equals(weather.status)){
                            PrefUtils.putString(WeatherActivity.this,"weather",responseText);
                            showWeatherInfo(weather);
                        }else{
                            ToastUtil.showSimpleToast(WeatherActivity.this,"获取天气信息失败");
                        }
                    }
                });
            }
        });
    }

    /**
     * 处理并展示weather中的数据
     *
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
        //标题及天气概况
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;

        tv_city_title.setText(cityName);
        tv_update_time.setText(updateTime);
        tv_degree.setText(degree);
        tv_weather_info.setText(weatherInfo);
        //近日天气
        ll_forecast_layout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, ll_forecast_layout, false);
            TextView tv_forecast_date = (TextView) view.findViewById(R.id.tv_forecast_date);
            TextView tv_forecast_info = (TextView) view.findViewById(R.id.tv_forecast_info);
            TextView tv_forecast_max = (TextView) view.findViewById(R.id.tv_forecast_max);
            TextView tv_forecast_min = (TextView) view.findViewById(R.id.tv_forecast_min);

            tv_forecast_date.setText(forecast.date);
            tv_forecast_info.setText(forecast.more.info);
            tv_forecast_max.setText(forecast.temperature.max);
            tv_forecast_min.setText(forecast.temperature.min);
            ll_forecast_layout.addView(view);
        }

        if (weather.aqi != null) {
            tv_aqi.setText(weather.aqi.city.aqi);
            tv_pm25.setText(weather.aqi.city.pm25);
        }

        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运动建议：" + weather.suggestion.sport.info;

        tv_comfort.setText(comfort);
        tv_car_wash.setText(carWash);
        tv_sport.setText(sport);

        ll_forecast_layout.setVisibility(View.VISIBLE);
    }
}
