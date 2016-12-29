package com.lyc.qweather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyc.qweather.R;
import com.lyc.qweather.base.BaseActivity;
import com.vstechlab.easyfonts.EasyFonts;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wayne on 2016/12/29.
 */

public class SplashActivity extends BaseActivity{
    @BindView(R.id.iv_splash_icon)
    ImageView iv_splash_icon;
    @BindView(R.id.tv_splash_text)
    TextView tv_splash_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        setView();
    }

    private void setView() {
        tv_splash_text.setText("Present for Amanda");
        tv_splash_text.setTypeface(EasyFonts.robotoBold(this));

        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(3000);
        aa.setFillAfter(true);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv_splash_text.startAnimation(aa);
    }
}
