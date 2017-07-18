package com.example.qiuchenluoye.hellotrueclient;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qiuchenluoye.hellotrueclient.ui.loginActivity;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.AllData.alldata;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.statusbar.StatusBarUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    ImageView nav_image;

    TextView userName, userInfo;

    LinearLayout exit, exitUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawerLayout,
                Color.argb(0, 0, 0, 0));

        initUI();

    }

    void initUI() {
        //INIT ui
        nav_image = (ImageView) findViewById(R.id.nav_HeaderImage);
        userName = (TextView) findViewById(R.id.nav_user_name);
        userInfo = (TextView) findViewById(R.id.nav_user_info);
        exit = (LinearLayout) findViewById(R.id.nav_program_exit);
        exitUser = (LinearLayout) findViewById(R.id.nav_program_exitUser);

        nav_image.setImageBitmap(alldata.d.nav_Image);
        userName.setText((alldata.d.isProgramer == true ? "开发者:" : "用户:")
                + alldata.d.mUsername);
        userInfo.setText("等级：" + alldata.d.mLevel + "|余额:" +
                alldata.d.mTotalMoney);

        exit.setOnClickListener(this);
        exitUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_program_exit:
                drawerLayout.closeDrawer(GravityCompat.END);
                moveTaskToBack(true);
                break;
            case R.id.nav_program_exitUser:
                drawerLayout.closeDrawer(GravityCompat.END);
                alldata.d.editor.putBoolean("isLogin", false);
                alldata.d.editor.apply();
                startActivity(new Intent(this, loginActivity.class));
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.push_fade_out);
                break;
        }
    }
}

