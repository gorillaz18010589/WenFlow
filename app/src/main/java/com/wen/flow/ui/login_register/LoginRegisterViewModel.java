package com.wen.flow.ui.login_register;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.wen.flow.model.Account;
import com.wen.flow.network.repository.LoginRegisterRepository;
import com.wen.flow.network.request.RegisterRequest;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.network.response.LoginModel;
import com.wen.flow.network.response.SendEmailCodeModel;
import com.wen.flow.network.viewmodel.BaseViewModel;

public class LoginRegisterViewModel extends BaseViewModel {
    private MutableLiveData<BaseResponse<Account>> registerData;
    private MutableLiveData<BaseResponse<SendEmailCodeModel>> sendEmailCodeData;
    private MutableLiveData<BaseResponse<LoginModel>> loginData;
    private LoginRegisterRepository loginRegisterRepository = new LoginRegisterRepository();


    public MutableLiveData<BaseResponse<Account>> register(String userName, String passWord, int emailEcode) {
        registerData = requestResponse(loginRegisterRepository.registers(userName, passWord, emailEcode), Account.class, true);
        return registerData;
    }

    public MutableLiveData<BaseResponse<SendEmailCodeModel>> sendEmailCode(String userName, String passWord, String passWordRepeat) {
        sendEmailCodeData = requestResponse(loginRegisterRepository.sendEmailCode(userName, passWord, passWordRepeat), SendEmailCodeModel.class, true);
        return sendEmailCodeData;
    }

    public MutableLiveData<BaseResponse<LoginModel>> login(String userName, String passWord, int loginType) {
        loginData = requestResponse(loginRegisterRepository.login(userName, passWord, loginType), LoginModel.class, true);
        return loginData;
    }

    public MutableLiveData<BaseResponse<Account>> getRegisterData() {
        if (registerData == null) {
            registerData = new MutableLiveData<>();
        }
        return registerData;
    }

    public MutableLiveData<BaseResponse<SendEmailCodeModel>> getSendEmailCodeData() {
        if (sendEmailCodeData == null) {
            sendEmailCodeData = new MutableLiveData<>();
        }
        return sendEmailCodeData;
    }


    public MutableLiveData<BaseResponse<LoginModel>> getLoginData() {
        if (loginData == null) {
            loginData = new MutableLiveData<>();
        }
        return loginData;
    }

    public LoginRegisterRepository getLoginRegisterRepository() {
        return loginRegisterRepository;
    }
}
