package com.example.qiuchenluoye.hellotrueclient.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.example.qiuchenluoye.hellotrueclient.MainActivity;
import com.example.qiuchenluoye.hellotrueclient.R;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.AllData.alldata;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.AllData.data;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.httpClient.httpClient;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        init();
    }

    void init() {
        if (alldata.d == null) {
            alldata.d = new data();
            alldata.d.sharedPreferences = SplashActivity.this.getSharedPreferences(
                    "QiuChenSet", MODE_PRIVATE);
            alldata.d.editor = alldata.d.sharedPreferences.edit();
        }
        final boolean isLogin = alldata.d.sharedPreferences.getBoolean("isLogin", false);
        alldata.d.APISESSION = alldata.d.sharedPreferences.getString("loginSession", "");

        final ImageView mSplashImage = (ImageView) findViewById(R.id.mSplashImageView);
        mSplashImage.setAlpha(0f);
        final AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        new Thread() {
            @Override
            public void run() {
                try {
                    alldata.d.nav_Image = httpClient.getBingImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SplashActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSplashImage.setImageBitmap(alldata.d.nav_Image);
                        mSplashImage.setAlpha(1f);
                        mSplashImage.setAnimation(alphaAnimation);
                        alphaAnimation.start();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i1;
                                if (isLogin) {
                                    //跳转登录页面
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                String[] s = alldata.d.ailezan.fastLogin(alldata.d.APISESSION);
                                                final Intent i;
                                                if (s != null) {
                                                    i = new Intent(SplashActivity.this, MainActivity.class);
                                                    alldata.d.mTotalMoney = s[1];
                                                    alldata.d.mLevel = s[2];
                                                    alldata.d.mGetPhoneMaxNums = s[3];
                                                    alldata.d.isProgramer = s[4].equals("软件商");
                                                    alldata.d.mUsername=alldata.d.sharedPreferences.getString("mUser","null");
                                                    alldata.d.mPassword=alldata.d.sharedPreferences.getString("mPass","null");
                                                } else {
                                                    alldata.d.editor.putBoolean("isLogin", false);
                                                    alldata.d.editor.apply();
                                                    i = new Intent(SplashActivity.this, loginActivity.class);
                                                }
                                                SplashActivity.this.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        startActivity(i);
                                                        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                                                        finish();
                                                    }
                                                });
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }.start();
                                } else {
                                    i1 = new Intent(SplashActivity.this, loginActivity.class);
                                    startActivity(i1);
                                    overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                                    finish();
                                }

                            }
                        }, 2000);
                    }
                });

            }
        }.start();
    }
}

