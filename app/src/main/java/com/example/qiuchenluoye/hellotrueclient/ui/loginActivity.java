package com.example.qiuchenluoye.hellotrueclient.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.LoginFilter;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qiuchenluoye.hellotrueclient.MainActivity;
import com.example.qiuchenluoye.hellotrueclient.R;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.AllData.alldata;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.AllData.loginResult;

import java.io.IOException;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;

    EditText user, pass, verifyCode;

    Button mLogin, mReg;

    ImageView verifyImageView;

    Thread setVerifyCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    void init() {
        user = (EditText) findViewById(R.id.login_user);
        pass = (EditText) findViewById(R.id.login_pass);
        verifyCode = (EditText) findViewById(R.id.login_captchastr);
        mLogin = (Button) findViewById(R.id.login_loginbtn);
        mReg = (Button) findViewById(R.id.login_registerBtn);
        verifyImageView = (ImageView) findViewById(R.id.login_captcha);

        mLogin.setOnClickListener(this);
        mReg.setOnClickListener(this);

        verifyImageView.setOnClickListener(this);

        setVerifyCodeImage = new Thread() {
            @Override
            public void run() {
                try {
                    alldata.d.captcha = alldata.d.ailezan.getCaptcha();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        verifyImageView.setAlpha(0f);
                        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
                        alphaAnimation.setDuration(500);
                        alphaAnimation.setFillAfter(true);

                        verifyImageView.setImageBitmap
                                (alldata.d.captcha);

                        verifyImageView.setAlpha(1f);
                        verifyImageView.setAnimation(alphaAnimation);
                        alphaAnimation.start();
                    }
                });
            }
        };
       setVerifyCodeImage.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_loginbtn:

                final String u = user.getText().toString();
                final String p = pass.getText().toString();
                final String vCode = verifyCode.getText().toString();

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            final loginResult result = alldata.d.ailezan.login(u, p, vCode);
                            if (result.statusCode != 1) {
                                setVerifyCodeImage.start();
                            }
                            loginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.statusCode == 1) {
                                        Toast.makeText(loginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                                        alldata.d.editor.putBoolean("isLogin", true);
                                        alldata.d.editor.putString("loginSession", result.reason);
                                        alldata.d.editor.putString("mUser", u);
                                        alldata.d.editor.putString("mPass", p);
                                        alldata.d.mUsername = u;
                                        alldata.d.mPassword = p;
                                        alldata.d.editor.apply();

                                        new Thread() {
                                            @Override
                                            public void run() {
                                                String[] s = new String[6];
                                                try {
                                                    s = alldata.d.ailezan.fastLogin(result.reason);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                alldata.d.mTotalMoney = s[1];
                                                alldata.d.mLevel = s[2];
                                                alldata.d.mGetPhoneMaxNums = s[3];
                                                alldata.d.isProgramer = s[4].equals("软件商");
                                                alldata.d.APISESSION = result.reason;
                                                Intent i = new Intent(loginActivity.this, MainActivity.class);
                                                startActivity(i);
                                                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                                                finish();
                                            }
                                        }.start();
                                    } else {
                                        Toast.makeText(loginActivity.this, result.reason, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.login_registerBtn:
                break;
            case R.id.login_captcha:
                //切换验证码显示
                setVerifyCodeImage.start();
                break;
        }
    }
}
