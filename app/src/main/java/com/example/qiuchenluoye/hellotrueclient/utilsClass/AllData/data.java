package com.example.qiuchenluoye.hellotrueclient.utilsClass.AllData;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class data {

  public Bitmap captcha = null;
  public Bitmap nav_Image = null;
  public SharedPreferences sharedPreferences;
  public SharedPreferences.Editor editor;
  public API_ailezan ailezan = new API_ailezan();
  public String APISESSION;

  public boolean isProgramer = false;
  public String mTotalMoney;
  public String mLevel;
  public String mGetPhoneMaxNums;

  public String mUsername;
  public String mPassword;

}
