package com.wen.flow.network.manager;

import com.wen.flow.framework.manager.app.AppManager;
import com.wen.flow.network.webapi.IServiceApi;

import retrofit2.Retrofit;

public class ApiManager {
    private static IServiceApi api = HttpManager.getInstance().create(IServiceApi.class);

    public static IServiceApi getApi() {
        return api;
    }

}
