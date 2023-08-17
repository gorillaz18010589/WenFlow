package com.wen.flow;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import com.tencent.mmkv.MMKV;
import com.wen.flow.framework.manager.app.ActivityManager;
import com.wen.flow.framework.manager.app.AppFrontBack;
import com.wen.flow.framework.manager.app.AppManager;
import com.wen.flow.network.interceptor.PublicParameterInterceptor;

import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {
    private static MyApplication instance;
    private OkHttpClient mOkHttpClient;
    public final static String TAG = "hank";
    public final static boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initAppSetting();
    }

    //初始話取得裝置資訊設定,及監聽
    private void initAppSetting() {
        initMMKV();
        appFrontBackRegister();
        registerActivityLifecycle();
        AppManager.init(this);
    }

    //註冊前後台監聽
    private void appFrontBackRegister() {
        AppFrontBack appFrontBack = new AppFrontBack();
        appFrontBack.register(this, new AppFrontBack.AppFrontBackListener() {
            @Override
            public void onFront(Activity activity) {

            }

            @Override
            public void onBack(Activity activity) {

            }
        });
    }

    //註冊ActivityManager管理監聽
    private void registerActivityLifecycle() {
        instance.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                Log.v("hank", "MyApplication onActivityStarted:" + activity.getLocalClassName());
                ActivityManager.push(activity);
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                ActivityManager.pop(activity);
            }
        });
    }

    private void initAppManager() {
        AppManager.init(this);
    }

    private void initMMKV() {
        MMKV.initialize(this);
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public Drawable getDrawableRes(@DrawableRes int id) {
        return this.getResources().getDrawable(id);
    }

    public String getStringByRes(@StringRes int id) {
        return this.getResources().getString(id);
    }

    /**
     * OkHttp初始化
     *
     * @return OkHttpClient
     */
    public OkHttpClient genericClient() {

        if (mOkHttpClient != null)
            return mOkHttpClient;

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.HEADERS;
        logInterceptor.setLevel(level);

        return mOkHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                // 設置讀寫時間
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .build();
    }


}
