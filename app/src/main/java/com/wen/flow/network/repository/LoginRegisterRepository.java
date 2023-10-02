package com.wen.flow.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.wen.flow.model.Account;
import com.wen.flow.network.manager.ApiManager;
import com.wen.flow.network.request.ApiRequest;
import com.wen.flow.network.request.LoginRequest;
import com.wen.flow.network.request.RegisterRequest;
import com.wen.flow.network.request.SendEmailCodeRequest;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.network.response.LoginModel;
import com.wen.flow.network.response.SendEmailCodeModel;
import com.wen.flow.ui.email_code.EmailCodeFragment;
import com.wen.flow.ui.login_register.LoginRegisterViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;

public class LoginRegisterRepository extends BaseRepository {

    public MutableLiveData<BaseResponse<Account>> register(String userName, String passWord, int passWordRepeat) {
        Map<String, String> maps = javaBeanToMap(new RegisterRequest(userName, passWord, passWordRepeat));
        return requestResponse(ApiManager.getApi().registers(maps));
    }

    public Observable<BaseResponse<Account>> registers(String userName, String passWord, int emailCode) {
        ApiRequest apiRequest = new RegisterRequest(userName, passWord, emailCode);
        return registers(apiRequest);
    }

    public Observable<BaseResponse<SendEmailCodeModel>> sendEmailCode(String userName, String passWord, String passWordRepeat) {
        ApiRequest apiRequest = new SendEmailCodeRequest(userName, passWord, passWordRepeat);
        return sendEmailCode(apiRequest);
    }

    public Observable<BaseResponse<LoginModel>> login(String userName, String passWord, int loginType) {
        ApiRequest apiRequest = new LoginRequest(userName, passWord, loginType);
        return login(apiRequest);
    }


}
