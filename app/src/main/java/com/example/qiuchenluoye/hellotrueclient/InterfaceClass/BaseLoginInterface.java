package com.example.qiuchenluoye.hellotrueclient.InterfaceClass;

import android.graphics.Bitmap;

import com.example.qiuchenluoye.hellotrueclient.utilsClass.AllData.loginResult;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.retDataClass.mQuirysInfo;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

/**
 * Author: QiuChenluoye
 * Time: 2017/07/21,下午 07:17
 * Func: 爱乐赞API登录接口类,内包含常见登录常量
 * Using: 无解释
 */

public interface BaseLoginInterface {
    int LOGIN_OK = 0;
    int LOGIN_CAPTCHA_ERROR = 1;
    int LOGIN_PASSWD_ERROR = 2;

    loginResult login(String user, String pass, String captcha) throws IOException;

    String[] fastLogin(String session) throws IOException;

    Bitmap getCaptcha() throws IOException;

    List<mQuirysInfo> getMessageListBetweenTimes(String session, String sTime, String eTime, String page) throws IOException;


}
