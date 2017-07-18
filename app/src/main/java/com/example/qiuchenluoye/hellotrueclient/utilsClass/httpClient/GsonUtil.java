package com.example.qiuchenluoye.hellotrueclient.utilsClass.httpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * ***************************************************
 * 版权所有： QiuChenLuoYe QQ:963084062
 * 创建日期： 2017.7.15 9:57
 * 创建作者： QiuChenLuoYe
 * 文件名称： com.qiuchen.luoye.hellotrue.Utils
 * 版本：1.0
 * 功能：解析JSON工具类
 * 最后修改时间：2017.7.15
 * 修改记录：第一次
 * ***************************************************
 */


public class GsonUtil {
    public static <T> T ResolveJsonA(String json, Class<T> type) {
        Gson gson = new Gson();
        T res = gson.fromJson(json, type);
        return res;
    }

    public static <T> List<T> ResolveJson(String json, Class<T[]> cs) {
        Gson gson = new Gson();
        T[] s = gson.fromJson(json, cs);
        return Arrays.asList(s);

    }

    public static <T> List<T> ResolveJsons(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }
}
