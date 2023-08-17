package com.wen.flow.network.manager;

import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.wen.flow.common.CommonConstant;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static com.wen.flow.MyApplication.TAG;
import static com.wen.flow.common.CommonConstant.HTTP_COOKIES_INFO;

/**
 * Cookies管理類別
 * 用於保存、獲取、清除和解析Cookies的工具類
 */
public class CookiesManager {

    /**
     * 保存Cookies
     *
     * @param cookies 要保存的Cookies字符串
     */
    public static void saveCookies(String cookies) {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode(HTTP_COOKIES_INFO, cookies);
    }

    /**
     * 獲取Cookies
     *
     * @return cookies
     */
    public static String getCookies() {
        MMKV mmkv = MMKV.defaultMMKV();
        return mmkv.decodeString(HTTP_COOKIES_INFO, "");
    }

    /**
     * 清除Cookies
     * 將保存的Cookies字符串清除，實現清除操作
     */
    public static void clearCookies() {
        saveCookies("");
    }

    /**
     * 解析Cookies
     *
     * @param cookies 要解析的Cookies列表
     * @return 解析後的Cookies字符串
     */
    public static String encodeCookie(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        HashSet<String> set = new HashSet<>();
        if (cookies != null) {
            for (String cookie : cookies) {
                String[] cookieParts = cookie.split(";");
                for (String part : cookieParts) {
                    set.add(part);
                }
            }
        }
        Log.v(TAG + cookies, "smy");
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String cookie = iterator.next();
            sb.append(cookie).append(";");
        }
        int last = sb.lastIndexOf(";");
        if (sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }
        return sb.toString();
    }
}

