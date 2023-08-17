package com.wen.flow.ui.login_register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.databinding.FragmentLoginBinding;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.model.Account;
import com.wen.flow.network.manager.ApiManager;
import com.wen.flow.network.request.RegisterRequest;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.support.toast.TipsToast;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> {
    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);

    }

    @Override
    protected void initListeners() {
        binding.btnLogin.setOnClickListener(v ->{
            loginRegisterViewModel.register("gorillaz1801058@gmail.com", "123456789", "123456789")
                    .observe(requireActivity(), new Observer<BaseResponse<Account>>() {
                        @Override
                        public void onChanged(BaseResponse<Account> accountBaseResponse) {
                            TipsToast.getInstance(MyApplication.getInstance()).showTips("onChanged");
                            RegisterRequest registerRequest = new Gson().fromJson(new Gson().toJson(accountBaseResponse),RegisterRequest.class);
                            KLog.json(TAG  + registerRequest ,new Gson().toJson(accountBaseResponse));
                        }
                    }) ;
//            Map<String, String> maps = new HashMap<>();
//            maps.put("userName", "gorillaz1801058@gmail.com");
//            maps.put("passWord", "123456789");
//            maps.put("passWordRepeat", "123456789");
//            ApiManager.getApi()
//                    .registers(maps)
//                    .subscribeOn(Schedulers.io())
////                    .observeOn(AndroidSchedulers.mainThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<BaseResponse<Account>>() {
//                        @Override
//                        public void onSubscribe(@NonNull Disposable d) {
//                            KLog.v(TAG +"registers -> onSubscribe()");
//                        }
//
//                        @Override
//                        public void onNext(@NonNull BaseResponse<Account> accountBaseResponse) {
//                            KLog.json(TAG +"registers -> onNext() accountBaseResponse:", new Gson().toJson(accountBaseResponse));
//
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable e) {
//                            KLog.v(TAG +"registers -> onError() e:"+ e.toString());
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            KLog.v(TAG +"registers -> onComplete()");
//                        }
//                    });
//            ApiManager.getApi().registers(maps).subscribe(
//                    response -> {
//                        TipsToast.getInstance(MyApplication.getInstance()).showTips("操你嗎");
//                    },
//                    throwable -> {
//                        // Handle the error here
//                        // You can show an error message or log the error
//                        throwable.printStackTrace();
//                    }
//            );
        });
    }
}