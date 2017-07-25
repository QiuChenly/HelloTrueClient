package com.example.qiuchenluoye.hellotrueclient.utilsClass.AllData;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class data {

  public Bitmap captcha = null;//验证码本地存储
  public Bitmap nav_Image = null;//标题栏图片
  public SharedPreferences sharedPreferences;//本地配置读取
  public SharedPreferences.Editor editor;//本地配置共享写入
  public API_ailezan ailezan = new API_ailezan();//爱了赞API接口
  public String APISESSION;//本地长存储Session

  //用户登录信息保存
  public boolean isProgramer = false;//是否为开发者
  public String mTotalMoney;//余额
  public String mLevel;//账户等级
  public String mGetPhoneMaxNums;//最大获取手机号码数量


  //用户用户名和密码存储
  public String mUsername;
  public String mPassword;

}