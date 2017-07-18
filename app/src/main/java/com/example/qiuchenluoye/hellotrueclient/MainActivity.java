package com.example.qiuchenluoye.hellotrueclient;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qiuchenluoye.hellotrueclient.adapter.mBillingInquiryAdapter;
import com.example.qiuchenluoye.hellotrueclient.ui.loginActivity;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.AllData.alldata;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.retDataClass.mQuirysInfo;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.statusbar.StatusBarUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    ImageView nav_image;

    TextView userName, userInfo;

    LinearLayout exit, exitUser, mBillingQ;

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

        mBillingQ = (LinearLayout) findViewById(R.id.nav_mBillingQ);

        mBillingQ.setOnClickListener(this);
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
            case R.id.nav_mBillingQ:
                cutView(1);

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
    }

    void cutView(int ViewID) {
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        switch (ViewID) {
            case 1:
                LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.minquiryinfoview, null);
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.contentView);
                frameLayout.removeAllViews();
                frameLayout.addView(linearLayout);
                break;
        }
        actions(ViewID);


    }

    mBillingInquiryAdapter mBillingInquiryAdapter;
    List<mQuirysInfo> ls;
    void actions(int id) {
        switch (id) {
            case 1:

                final RecyclerView recyclerView = (RecyclerView) findViewById
                        (R.id.mInquiryInfoRecyclerView);
                mBillingInquiryAdapter = new mBillingInquiryAdapter();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            List<mQuirysInfo> l = alldata.d.ailezan.getMessageListBetweenTimes(
                                    alldata.d.APISESSION, "2017-06-15 00:00:00",
                                    "2017-07-18 00:00:00", "1");
                            mBillingInquiryAdapter.setData(l);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setHasFixedSize(true);
                                recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                                    @Override
                                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                                        outRect.left = 15;
                                        outRect.bottom = 10;
                                        outRect.top = 10;
                                        outRect.right = 15;

                                    }
                                });
                                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                recyclerView.setAdapter(mBillingInquiryAdapter);

                            }
                        });
                    }
                }.start();

                break;

        }
    }


}