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
import com.wen.flow.network.viewmodel.BaseViewModel;

public class LoginRegisterViewModel extends BaseViewModel {
    private MutableLiveData<BaseResponse<Account>> registerData;
    private LoginRegisterRepository loginRegisterRepository = new LoginRegisterRepository();


    public MutableLiveData<BaseResponse<Account>> register(String userName, String passWord, String passWordRepeat) {
//        registerData = requestResponse(loginRegisterRepository.registers(new RegisterRequest(userName, passWord, passWordRepeat)));
        registerData = requestResponse(loginRegisterRepository.registers(userName,passWord,passWordRepeat));
        return registerData;
    }

}
