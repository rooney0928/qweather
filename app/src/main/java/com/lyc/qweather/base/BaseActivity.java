package com.lyc.qweather.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lyc.qweather.util.NetworkUtil;
import com.lyc.qweather.util.ToastUtil;

import butterknife.ButterKnife;

/**
 * Created by wayne on 2016/12/29.
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.e("text", "activity  onResume ");
        checkNetworked();
    }

    protected boolean checkNetworked() {
        if (!NetworkUtil.checkNetwork(this)) {
            ToastUtil.showSimpleToast(getApplicationContext(), "手机无可用网络！");
            return false;
        }
        return true;
    }
}
