package com.wen.flow.network.repository;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.model.Account;
import com.wen.flow.network.error.ApiException;
import com.wen.flow.network.manager.ApiManager;
import com.wen.flow.network.request.ApiRequest;
import com.wen.flow.network.request.RegisterRequest;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.network.response.LoginModel;
import com.wen.flow.network.response.SendEmailCodeModel;
import com.wen.flow.network.webapi.IServiceApi;
import com.wen.flow.ui.login_register.LoginRegisterViewModel;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.Callable;

import static com.wen.flow.MyApplication.TAG;

public class BaseRepository {
    /*
    * //每個api的RequestBean資料,如果有重複的key,新的資料將取代舊的資料
    *
    * */
    HashMap<String, ApiRequest> apiRequestDatas = new HashMap<>();


    public <T> MutableLiveData<BaseResponse<T>> requestResponse(Observable<BaseResponse<T>> requestCall) {
        MutableLiveData<BaseResponse<T>> resultLiveData = new MutableLiveData<>();

        try {
            Observable<BaseResponse<T>> responseSingle = requestCall;
            responseSingle
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response != null) {
                            if (response.isResult()) {
                                resultLiveData.setValue(response);
                            } else {
                                KLog.json(TAG + "requestResponse !isResult->" + new Gson().toJson(response));
                            }
                        } else {
                            resultLiveData.setValue(null);
                        }
                    }, throwable -> {
                        KLog.v(TAG + "requestResponse throwable ->" + throwable.getMessage());
                        // Handle error here
                    });
        } catch (Exception e) {
            // Handle exception here
            KLog.v(TAG + "requestResponse Exception ->" + e.toString());
        }

        return resultLiveData;
    }


    /*3.javabean轉成Map
     *@param =>Object javabean:預轉成Map的javabean
     *@return =>Map<String, String>:回傳JsonMapo格式
     * */
    public Map<String, String> javaBeanToMap(Object javabean) {
        Gson gson = new Gson();

        //將傳進來的bean轉成Json
        String json = gson.toJson(javabean);//toJson(Object src):將物件轉成字串Json(要被轉成Json的物件)(回傳String)
        KLog.v(TAG, "javaBeanToMap:" + json); //{"cart_id":"aaa","}

        //將Json格式Sting資料,轉成Map<String, String>
        //fromJson(String json, Type typeOfT):從Json字串解析回傳到資料結構上(1.要轉型的Json字串,2.要轉型的資料結構類型)(回傳<T> T)
        Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType()); //getType():取得底層資料結構的種類(回傳Type)

        KLog.v(TAG, "javaBeanToMap:" + map.toString());
        return map;
    }

    public Observable<BaseResponse<LoginModel>> login(ApiRequest apiRequest) {
        Map<String, String> maps = javaBeanToMap(apiRequest);
        addApiRequest(apiRequest);
        return ApiManager.getApi().login(maps);
    }

    public Observable<BaseResponse<Account>> registers(ApiRequest apiRequest) {
        Map<String, String> maps = javaBeanToMap(apiRequest);
        addApiRequest(apiRequest);
        return ApiManager.getApi().registers(maps);
    }

    public Observable<BaseResponse<SendEmailCodeModel>> sendEmailCode(ApiRequest apiRequest) {
        Map<String, String> maps = javaBeanToMap(apiRequest);
        addApiRequest(apiRequest);
        return ApiManager.getApi().sendEmailCode(maps);
    }

    /*
    * ApiRequests Start
    */
    private void addApiRequest(ApiRequest apiRequest) {
        apiRequestDatas.put(apiRequest.getTagName(), apiRequest);
        KLog.v("apiRequestDatas.size:" + apiRequestDatas.size());
        KLog.json(new Gson().toJson(apiRequestDatas));
    }

    public ApiRequest getApiRequestByClassTag(String classTag) {
        if (apiRequestDatas.size() < 0) return null;
        for (int i = 0; i < apiRequestDatas.size(); i++) {
            if (apiRequestDatas.containsKey(classTag)) {
                KLog.json("getApiRequestByClassTag" + new Gson().toJson(apiRequestDatas.get(classTag)));
                return apiRequestDatas.get(classTag);
            }
        }
        return null;
    }

    public void apiRequestsClean() {
        if (apiRequestDatas.size() > 0) apiRequestDatas.clear();
    }

    public void apiRequestsReMove(String deleteTagName) {
        if (apiRequestDatas.size() > 0) apiRequestDatas.remove(deleteTagName);
    }

    /*
     * ApiRequests End
     */
}
