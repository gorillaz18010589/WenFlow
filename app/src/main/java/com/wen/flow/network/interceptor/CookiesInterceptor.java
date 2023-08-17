package com.wen.flow.network.interceptor;

import android.util.Log;

import com.wen.flow.network.manager.CookiesManager;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.wen.flow.MyApplication.TAG;
import static com.wen.flow.network.constant.HttpConstant.KEY_SAVE_USER_LOGIN;
import static com.wen.flow.network.constant.HttpConstant.KEY_SAVE_USER_REGISTER;
import static com.wen.flow.network.constant.HttpConstant.KEY_SET_COOKIE;

/**
 * Cookies攔截器
 * 這個攔截器用於處理網絡請求和響應，特別是處理Cookies的保存和提取。
 */

public class CookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();

        Response response = chain.proceed(newBuilder.build());
        String url = request.url().toString();
        String host = request.url().host();

        // 如果是登錄或註冊請求，並且響應中包含了 Cookie（使用名稱為 KEY_SET_COOKIE 的 Header）
        if ((url.contains(KEY_SAVE_USER_LOGIN) || url.contains(KEY_SAVE_USER_REGISTER))
                && !response.headers(KEY_SET_COOKIE).isEmpty()) {
            List<String> cookies = response.headers(KEY_SET_COOKIE);

            // 將響應中的 Cookie 轉換為一個字符串並保存
            String cookiesStr = CookiesManager.encodeCookie(cookies);
            CookiesManager.saveCookies(cookiesStr);
            Log.v(TAG + cookies, "smy");
        }
        return response;
    }
}


