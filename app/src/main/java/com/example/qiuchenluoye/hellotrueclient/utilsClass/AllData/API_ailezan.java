package com.example.qiuchenluoye.hellotrueclient.utilsClass.AllData;

import android.graphics.Bitmap;


import com.example.qiuchenluoye.hellotrueclient.utilsClass.httpClient.GsonUtil;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.httpClient.ResponseData;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.httpClient.httpClient;
import com.example.qiuchenluoye.hellotrueclient.utilsClass.retDataClass.mQuirysInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Auther: cheny
 * CreateDate 2017/7/1.
 */

public class API_ailezan {
    public loginResult login(String user, String pass, String vcode) throws IOException {
        String url = "http://api2.hellotrue.com/api/do.php?web=true&action=loginIn&name=" +
                user + "&password=" + pass + "&captcha=" + vcode;
        String res = httpClient.Request_Str(url, httpClient.cookies);
        String[] check = res.split("\\|");
        loginResult result = new loginResult();
        if (check.length == 2) {
            result.reason = check[1];
            switch (check[0]) {
                case "0":
                    result.statusCode = 0;
                    break;
                case "1":
                    result.statusCode = 1;
                    break;
            }
        } else {
            result.reason = "服务器连接失败!";
            result.statusCode = 404;
        }
        return result;
    }

    public String[] fastLogin(String session) throws IOException {
        String url = "http://api2.hellotrue.com/api/do.php?action=getSummary&token=" + session;
        ResponseData result = httpClient.Request(url, httpClient.cookies);
        String[] res = result.responseText.split("\\|");
        if (Objects.equals(res[0], "1")) {
            return res;
        } else {
            return null;
        }
    }

    public Bitmap getCaptcha() throws IOException {
        String url = "http://api2.hellotrue.com/i/captcha.php?r=";
        return httpClient.Request_Image(url, httpClient.cookies);
    }

    public List<mQuirysInfo> getMessageListBetweenTimes(String session, String sTime, String eTime, String page) throws IOException {
        String url = "http://api2.hellotrue.com/api/do.php?action=" +
                "messageList&phone=&sTime=" + httpClient.EncodeStr(sTime, "UTF-8")
                + "&eTime=" + httpClient.EncodeStr(eTime, "UTF-8") + "&page=" + page + "" +
                "&token=" + session;
        String[] result = httpClient.Request_Str(url, httpClient.cookies).split("\\|", 2);
        if (Objects.equals(result[0], "1")) {
            if (Objects.equals(result[1], "[]")) {
                return new ArrayList<mQuirysInfo>();
            }
            List<mQuirysInfo> mInfo = GsonUtil.ResolveJson(result[1],mQuirysInfo[].class);
            return mInfo;
        } else {
            return null;
        }
    }
}
