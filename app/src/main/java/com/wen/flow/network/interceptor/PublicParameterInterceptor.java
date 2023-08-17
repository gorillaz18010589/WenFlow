package com.wen.flow.network.interceptor;

import com.wen.flow.MyApplication;
import com.wen.flow.framework.manager.app.AppManager;
import com.wen.flow.support.util.DeviceInfoUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.wen.flow.MyApplication.getInstance;

/*
* 這個攔截器主要用於在每個請求的標頭中添加公共參數，
* 例如設備型號、應用版本、設備 ID 等等。攔截器會在每個請求發送之前被調用，它可以在請求的標頭中添加自定義的參數，
* 以達到統一管理的目的。在 Java 中，我們使用 try-catch 處理了裝置名稱字串的 URL 編碼，以防止不支援的編碼引起的異常。
請確保 AppManager、SumAppHelper 和 DeviceInfoUtils 等類別在 Java 環境中也能正確地被使用，並且將相關的類別導入。

* */
public class PublicParameterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        requestBuilder.addHeader("device_typ", "android");
        requestBuilder.addHeader("app_version", AppManager.getAppVersionName(getInstance()));
        requestBuilder.addHeader("device_id", DeviceInfoUtils.getAndroidId());
        requestBuilder.addHeader("device_os_version", AppManager.getDeviceBuildRelease());

        // 構建裝置名稱字串，格式為品牌_型號
        String deviceNameStr = AppManager.getDeviceBuildBrand() + "_" + AppManager.getDeviceBuildModel();
        try {
            // 對裝置名稱進行 URL 編碼
            deviceNameStr = URLEncoder.encode(deviceNameStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        requestBuilder.addHeader("device-name", deviceNameStr);
        return chain.proceed(requestBuilder.build());
    }
}
