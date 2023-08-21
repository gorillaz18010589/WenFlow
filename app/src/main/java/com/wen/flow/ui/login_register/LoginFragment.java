package com.wen.flow.ui.login_register;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.wen.flow.framework.navigation.navigator.ReplaceFragment;
import com.wen.flow.model.Account;
import com.wen.flow.network.manager.ApiManager;
import com.wen.flow.network.request.RegisterRequest;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.support.toast.TipsToast;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginFragment extends BaseFragment<FragmentLoginBinding> {
    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.v("生命週期 onCreate()");
    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.v("生命週期 onResume()");
    }

    @Override
    public void onStart() {
        super.onStart();
        KLog.v("生命週期 onStart()");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KLog.v("生命週期 onViewCreated()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        KLog.v("生命週期 onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.v("生命週期 onDestroy()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KLog.v("生命週期 onDestroyView()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        KLog.v("生命週期 onAttach()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        KLog.v("生命週期 onDetach()");
    }

    @Override
    public void onStop() {
        super.onStop();
        KLog.v("生命週期 onStop()");
    }

    @Override
    public void onPause() {
        super.onPause();
        KLog.v("生命週期 onPause()");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
        loginRegisterViewModel = new ViewModelProvider(getActivity()).get(LoginRegisterViewModel.class);
    }

    @Override
    protected void initListeners() {
        binding.btnLogin.setOnClickListener(v -> {
            loginRegisterViewModel.register("gorillaz180105@gmail.com", "123456789", "123456789")
                    .observe(requireActivity(), new Observer<BaseResponse<Account>>() {
                        @Override
                        public void onChanged(BaseResponse<Account> accountBaseResponse) {
//                            TestBaseViewModel(accountBaseResponse);
                        }
                    });
        });
    }

    private void TestBaseViewModel(BaseResponse<Account> accountBaseResponse) {
        boolean isResult = accountBaseResponse.isResult();
        if (isResult) {
            TipsToast.getInstance(MyApplication.getInstance()).showTips("成功");
            KLog.v("loginRegisterViewModel->" + String.format("isResult:%s", isResult));
        } else {
            BaseResponse.ErrorData errorData = accountBaseResponse.getErrorData();
            KLog.v("loginRegisterViewModel->" + String.format("isResult:%s, errorMsg:%s, errorCode:%s", isResult, errorData.getErrMsg(), errorData.getErrCode()));
        }
    }
}