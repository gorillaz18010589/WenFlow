package com.wen.flow.network;

import com.wen.flow.model.Account;

import java.util.Map;
import io.reactivex.Observable;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IServiceApi {

    @FormUrlEncoded
    @POST("account/register")
    Observable<Account> register(@FieldMap Map<String, String> params);
}
