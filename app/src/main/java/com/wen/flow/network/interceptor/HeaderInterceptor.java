package com.wen.flow.network.interceptor;

import android.util.Log;

import com.wen.flow.network.manager.CookiesManager;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import static com.wen.flow.MyApplication.TAG;
import static com.wen.flow.network.constant.HttpConstant.ARTICLE_WEBSITE;
import static com.wen.flow.network.constant.HttpConstant.COIN_WEBSITE;
import static com.wen.flow.network.constant.HttpConstant.COLLECTION_WEBSITE;
import static com.wen.flow.network.constant.HttpConstant.KEY_COOKIE;
import static com.wen.flow.network.constant.HttpConstant.NOT_COLLECTION_WEBSITE;

/**
 * 头信息拦截器
 * 添加头信息
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();
        newBuilder.addHeader("Content-type", "application/json; charset=utf-8");

        String host = request.url().host();
        String url = request.url().toString();

        //给有需要的接口添加Cookies
        if (host != null && (!url.isEmpty())
                && (url.contains(COLLECTION_WEBSITE)
                || url.contains(NOT_COLLECTION_WEBSITE)
                || url.contains(ARTICLE_WEBSITE)
                || url.contains(COIN_WEBSITE))) {
            String cookies = CookiesManager.getCookies();
            Log.v(TAG + "HeaderInterceptor:cookies:" + cookies, "smy");
            if (cookies != null && !cookies.isEmpty()) {
                newBuilder.addHeader(KEY_COOKIE, cookies);
            }
        }
        return chain.proceed(newBuilder.build());
    }
}
