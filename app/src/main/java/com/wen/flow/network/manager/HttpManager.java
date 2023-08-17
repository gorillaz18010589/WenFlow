package com.wen.flow.network.manager;

import com.wen.flow.MyApplication;
import com.wen.flow.network.constant.HttpConstant;
import com.wen.flow.network.error.ERROR;
import com.wen.flow.network.interceptor.CookiesInterceptor;
import com.wen.flow.network.interceptor.HeaderInterceptor;
import com.wen.flow.network.interceptor.PublicParameterInterceptor;
import com.wen.flow.support.toast.TipsToast;
import com.wen.flow.support.util.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.wen.flow.MyApplication.isDebug;

public class HttpManager {
    private static Retrofit mRetrofit;
    private static HttpManager instance;

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }


    public HttpManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConstant.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 獲取 apiService
     */
    public <T> T create(Class<T> apiService) {
        return mRetrofit.create(apiService);
    }


    /**
     * 獲取 okHttpClient
     */
    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(60 * 1, TimeUnit.SECONDS)
                .readTimeout(30 * 1, TimeUnit.SECONDS);

        // 添加參數攔截器
//        okHttpBuilder.addInterceptor(new CookiesInterceptor());
//        okHttpBuilder.addInterceptor(new HeaderInterceptor());
//        okHttpBuilder.addInterceptor(new PublicParameterInterceptor());

        //Log攔截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(isDebug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.HEADERS);
        okHttpBuilder.addInterceptor(httpLoggingInterceptor);

        //網路狀態攔截器// 網絡狀態攔截器
//               okHttpBuilder.addInterceptor(new Interceptor() {
//                   @Override
//                   public Response intercept(Chain chain) throws IOException {
//                       if (NetworkUtils.isConnected(MyApplication.getInstance())) {
//                           okhttp3.Request request = chain.request();
//                           return chain.proceed(request);
//                       } else {
//                           TipsToast.getInstance(MyApplication.getInstance()).showTips(ERROR.NETWORD_ERROR.getErrMsg());
//                           throw new NoNetWorkException(ERROR.NETWORD_ERROR);
//                       }
//                   }
//               });

        return okHttpBuilder.build();
    }


}
