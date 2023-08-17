package com.wen.flow.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.wen.flow.model.Account;
import com.wen.flow.network.manager.ApiManager;
import com.wen.flow.network.request.ApiRequest;
import com.wen.flow.network.request.RegisterRequest;
import com.wen.flow.network.response.BaseResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;

public class LoginRegisterRepository extends BaseRepository {

    public MutableLiveData<BaseResponse<Account>> register(String userName, String passWord, String passWordRepeat) {
        Map<String, String> maps = javaBeanToMap(new RegisterRequest(userName, passWord, passWordRepeat));
        return requestResponse(ApiManager.getApi().registers(maps));
    }

    public Observable<BaseResponse<Account>> registers(String userName, String passWord, String passWordRepeat) {
        ApiRequest apiRequest = new RegisterRequest(userName, passWord, passWordRepeat);
        return registers(apiRequest);
    }
}
