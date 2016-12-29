package com.lyc.qweather.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lyc.qweather.R;
import com.lyc.qweather.base.BaseActivity;
import com.lyc.qweather.gson.Weather;
import com.lyc.qweather.util.PrefUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String weather = PrefUtils.getString(this, "weather", null);
        if (weather != null) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
