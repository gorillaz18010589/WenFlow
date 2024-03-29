package com.wen.flow.network.webapi;

import com.wen.flow.model.Account;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.network.response.LoginModel;
import com.wen.flow.network.response.Mongodb;
import com.wen.flow.network.response.SendEmailCodeModel;

import java.util.Map;


import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServiceApi {


    @FormUrlEncoded
    @POST("account/register")
    Observable<Account> register(@FieldMap Map<String, String> params);


    /*
     * 註冊帳號
     *
     */
    @FormUrlEncoded
    @POST("account/register")
    Observable<BaseResponse<Account>> registers(@FieldMap Map<String, String> params);


    /*
     * 寄驗證碼至使用者email
     *
     */
    @FormUrlEncoded
    @POST("account/sendemailcode")
    Observable<BaseResponse<SendEmailCodeModel>> sendEmailCode(@FieldMap Map<String, String> params);


    /*
     * 登入
     *
     */
    @FormUrlEncoded
    @POST("account/login")
    Observable<BaseResponse<LoginModel>> login(@FieldMap Map<String, String> params);

    @GET("search/mongodb/{page}")
    Observable<Mongodb> getAllPeople(@Path("page") int page);
}
