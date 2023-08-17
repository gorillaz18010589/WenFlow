package com.wen.flow.network.webapi;

import com.wen.flow.model.Account;
import com.wen.flow.network.response.BaseResponse;

import java.util.Map;


import io.reactivex.Observable;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IServiceApi {

    @FormUrlEncoded
    @POST("account/register")
    Observable<Account> register(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("account/register")
    Observable<BaseResponse<Account>> registers(@FieldMap Map<String, String> params);
}
