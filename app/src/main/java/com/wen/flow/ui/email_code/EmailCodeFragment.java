package com.wen.flow.ui.email_code;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.databinding.FragmentEmailCodeBinding;
import com.wen.flow.enums.RegexPatternEnum;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.framework.navigation.navigator.ReplaceFragment;
import com.wen.flow.model.Account;
import com.wen.flow.network.request.SendEmailCodeRequest;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.network.response.LoginModel;
import com.wen.flow.network.response.SendEmailCodeModel;
import com.wen.flow.network.webapi.BaseApi;
import com.wen.flow.network.webapi.IServiceApi;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.support.util.RegexUtils;
import com.wen.flow.ui.login_register.LoginRegisterViewModel;

import java.util.HashMap;
import java.util.Map;

@ReplaceFragment
public class EmailCodeFragment extends BaseFragment<FragmentEmailCodeBinding> {
    private LoginRegisterViewModel loginRegisterViewModel;
    private SendEmailCodeRequest sendEmailCodeRequest;
    private String emailCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sendEmailCodeRequest = (SendEmailCodeRequest) getArguments().getSerializable(new SendEmailCodeRequest().getTagName());
            KLog.json(new Gson().toJson(sendEmailCodeRequest));
        }
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
        return R.layout.fragment_email_code;
    }

    @Override
    protected void initView(View rootView) {
        initPinView();
    }

    private void initPinView() {
        binding.pinView.setAnimationEnable(true);
    }

    @Override
    protected void initData() {
        loginRegisterViewModel = new ViewModelProvider(getActivity()).get(LoginRegisterViewModel.class);
    }

    @Override
    protected void initListeners() {
        binding.pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v(MyApplication.TAG + TAG, "s:" + s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.btnRegister.setOnClickListener(onClickListener);
        binding.tvReSendEmailCode.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnRegister:
//                    if(checkFormat(R.id.btnRegister)){
//                        int emailCode = Integer.parseInt(binding.pinView.getText().toString());
//                        register(sendEmailCodeRequest.getUserName(), sendEmailCodeRequest.getPassWord(),emailCode);
//                    }
                    String emailCode = binding.pinView.getText().toString();
                    KLog.v("emailCode:" + emailCode);
                    if (TextUtils.isEmpty(emailCode)) {
                        showToast(getResources().getString(R.string.s_email_code_null_error));
                        return;
                    }
                    KLog.v("emailCode:" + String.valueOf(emailCode));
                    if (emailCode.length() < 6) {
                        showToast(getResources().getString(R.string.s_email_code_null_error));
                        return;
                    }
                    register(sendEmailCodeRequest.getUserName(), sendEmailCodeRequest.getPassWord(), Integer.parseInt(emailCode));
                    break;

                case R.id.tvReSendEmailCode:
                    sendEmailCode(sendEmailCodeRequest.getUserName(), sendEmailCodeRequest.getPassWord(), sendEmailCodeRequest.getPassWordRepeat());
                    break;
            }
        }
    };

    int loginType =1;
    public void register(String userName, String passWord, int emailEcode) {
        Log.v(TAG, "register ->");
        loginRegisterViewModel.register(userName, passWord, emailEcode).observe(getActivity(), new Observer<BaseResponse<Account>>() {
            @Override
            public void onChanged(BaseResponse<Account> accountBaseResponse) {
                if (accountBaseResponse.isResult()) {
                    login(userName,passWord,loginType);
                } else {
                    binding.pinView.setText("");
                }
            }
        });
    }

    private boolean checkFormat(int viewID) {
        boolean formatOK = true;

        switch (viewID) {
            case R.id.btnRegister:
                String emailCode = binding.pinView.getText().toString();
                if (TextUtils.isEmpty(emailCode)) {
                    showWarningTips(getResources().getString(R.string.s_email_code_null_error));
                    formatOK = false;
                } else if (!TextUtils.isEmpty(emailCode) && !RegexUtils.censorText(emailCode, RegexPatternEnum.EMAIL_CODE)) {
                    showWarningTips(getResources().getString(R.string.s_email_code_null_error));
                    formatOK = false;
                } else {
                    KLog.v("checkFormat()");
                }
                break;

        }
        return formatOK;
    }

    private void sendEmailCode(String userName, String passWord, String passWordRepeat) {
        Log.v(TAG, "sendEmailCode -> userName:" + userName + ", passWord:" + passWord + ", passWordRepeat:" + passWordRepeat);
        loginRegisterViewModel.sendEmailCode(userName, passWord, passWordRepeat).observe(requireActivity(), new Observer<BaseResponse<SendEmailCodeModel>>() {
            @Override
            public void onChanged(BaseResponse<SendEmailCodeModel> sendEmailCodeModelBaseResponse) {
                if (sendEmailCodeModelBaseResponse.isResult()) {
                    showSuccessTips(MyApplication.getInstance().getStringByRes(R.string.s_s_resend_email_code_success));
                }
            }
        });
    }

    private void login(String userName, String passWord, int loginType) {
        loginRegisterViewModel.login(userName, passWord, loginType).observe(requireActivity(), new Observer<BaseResponse<LoginModel>>() {
            @Override
            public void onChanged(BaseResponse<LoginModel> loginModelBaseResponse) {
                if (loginModelBaseResponse.isResult()) {
                    LoginModel loginModel = loginModelBaseResponse.getBean();
                    MyApplication.getInstance().saveLoginData(loginModel);
                    Intent intent = mActivity.getIntent();
                    intent.putExtra(loginModel.getTagName(), (Parcelable) loginModel);
                    mActivity.setResult(Activity.RESULT_OK, intent);
                    mActivity.finish();
                }
            }
        });
    }
}