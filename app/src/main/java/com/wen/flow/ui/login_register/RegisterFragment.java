package com.wen.flow.ui.login_register;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.databinding.FragmentRegisterBinding;
import com.wen.flow.enums.RegexPatternEnum;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.framework.navigation.navigator.ReplaceFragment;
import com.wen.flow.model.Account;
import com.wen.flow.network.repository.BaseRepository;
import com.wen.flow.network.request.ApiRequest;
import com.wen.flow.network.request.SendEmailCodeRequest;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.network.response.SendEmailCodeModel;
import com.wen.flow.network.webapi.BaseApi;
import com.wen.flow.network.webapi.IServiceApi;
import com.wen.flow.support.base.BaseBindingActivity;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.support.util.RegexUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Single;


public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {
    private int All_VIEW = -999;
    private LoginRegisterViewModel loginRegisterViewModel;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View rootView) {
    }

    private void sendEmailCode() {
        String userName = binding.editEmail.getText().toString();
        String passWord = binding.editPassWord.getText().toString();
        String passWordRepeat = binding.editPassWordConfirm.getText().toString();
        if (checkFormat(All_VIEW)) {
//            register(userName, passWord, passWordRepeat);
            sendEmailCode(userName, passWord, passWordRepeat);
        } else {
            showToast(MyApplication.getInstance().getStringByRes(R.string.s_input_null_error));
            Log.v(TAG, "錯誤");
        }
    }

    @Override
    protected void initData() {
        loginRegisterViewModel = new ViewModelProvider(getActivity()).get(LoginRegisterViewModel.class);
    }

    @Override
    protected void initListeners() {
        binding.btnSend.setOnClickListener(v -> {
            sendEmailCode();
        });
        binding.textInputEmailLayout.setEndIconOnClickListener(v -> {
            emailClean();
        });
        binding.editEmail.setOnFocusChangeListener(onFocusChangeListener);
        binding.editPassWord.setOnFocusChangeListener(onFocusChangeListener);
        binding.editPassWordConfirm.setOnFocusChangeListener(onFocusChangeListener);
        binding.editEmail.addTextChangedListener(emailTextChangeListener);
        binding.editPassWord.addTextChangedListener(passWordTextChangeListener);
        binding.editPassWordConfirm.addTextChangedListener(passWordConfirmTextChangeListener);
//        binding.editEmail.setOnEditorActionListener(onEmailEditorActionListener);
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            Log.v(TAG, "onFocusChangeListener():" + hasFocus);
            switch (v.getId()) {
                case R.id.editEmail:
                    if (!hasFocus) {
                        checkFormat(v.getId());
                    }
                    Log.v(TAG, "onFocusChangeListener() editEmail:" + hasFocus);
                    break;
                case R.id.editPassWord:
                    if (!hasFocus) {
                        checkFormat(v.getId());
                    }
                    Log.v(TAG, "onFocusChangeListener() editPassWord:" + hasFocus);
                    break;
                case R.id.editPassWordConfirm:
                    if (!hasFocus) {
                        checkFormat(v.getId());
                    }
                    Log.v(TAG, "onFocusChangeListener() editPassWordConfirm:" + hasFocus);
                    break;
            }
        }
    };

    private boolean checkFormat(int viewID) {
        boolean formatOK = true;

        switch (viewID) {
            case R.id.editEmail:
                String email = binding.editEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    binding.textInputEmailLayout.setError(getResources().getString(R.string.s_email_input_null_erro));
                    formatOK = false;
                } else if (!TextUtils.isEmpty(email) && !RegexUtils.censorText(email, RegexPatternEnum.EMAIL)) {
                    binding.textInputEmailLayout.setError(getResources().getString(R.string.s_email_input_type_erro));
                    formatOK = false;
                } else {
                    binding.textInputEmailLayout.setErrorEnabled(false);
                }
                break;
            case R.id.editPassWord:
                String passWord = binding.editPassWord.getText().toString();
                if (TextUtils.isEmpty(passWord)) {
                    binding.textInputPassWordLayout.setError(getResources().getString(R.string.s_password_input_null_erro));
                    formatOK = false;
                } else if (!TextUtils.isEmpty(passWord) && !RegexUtils.censorText(passWord, RegexPatternEnum.PASS_WORD)) {
                    binding.textInputPassWordLayout.setError(getResources().getString(R.string.s_password_input_type_erro));
                    formatOK = false;
                } else {
                    binding.textInputPassWordLayout.setErrorEnabled(false);
                }
                break;
            case R.id.editPassWordConfirm:
                String passWordConfirm = binding.editPassWordConfirm.getText().toString();
                if (TextUtils.isEmpty(passWordConfirm)) {
                    binding.textInputPassWordConfrimLayout.setError(getResources().getString(R.string.s_password_confrim_input_null_erro));
                    formatOK = false;
                } else if (!passWordConfirm.equals(binding.editPassWord.getText().toString())) {
                    binding.textInputPassWordConfrimLayout.setError(getResources().getString(R.string.s_password_confrim_input_inconsistent_erro));
                    formatOK = false;
                } else if (!TextUtils.isEmpty(passWordConfirm) && !RegexUtils.censorText(passWordConfirm, RegexPatternEnum.PASS_WORD)) {
                    binding.textInputPassWordConfrimLayout.setError(getResources().getString(R.string.s_password_input_type_erro));
                    formatOK = false;
                } else {
                    binding.textInputPassWordConfrimLayout.setErrorEnabled(false);
                }
                break;

            default:
                String defaultEmail = binding.editEmail.getText().toString();
                if (TextUtils.isEmpty(defaultEmail)) {
                    binding.textInputEmailLayout.setError(getResources().getString(R.string.s_email_input_null_erro));
                    formatOK = false;
                } else if (!TextUtils.isEmpty(defaultEmail) && !RegexUtils.censorText(defaultEmail, RegexPatternEnum.EMAIL)) {
                    binding.textInputEmailLayout.setError(getResources().getString(R.string.s_email_input_type_erro));
                    formatOK = false;
                } else {
                    binding.textInputEmailLayout.setErrorEnabled(false);
                }

                String deFaultPassWord = binding.editPassWord.getText().toString();
                if (TextUtils.isEmpty(deFaultPassWord)) {
                    binding.textInputPassWordLayout.setError(getResources().getString(R.string.s_password_input_null_erro));
                    formatOK = false;
                } else if (!TextUtils.isEmpty(deFaultPassWord) && !RegexUtils.censorText(deFaultPassWord, RegexPatternEnum.PASS_WORD)) {
                    binding.textInputPassWordLayout.setError(getResources().getString(R.string.s_password_input_type_erro));
                    formatOK = false;
                } else {
                    binding.textInputPassWordLayout.setErrorEnabled(false);
                }

                String defaultPassWordConfirm = binding.editPassWordConfirm.getText().toString();
                if (TextUtils.isEmpty(defaultPassWordConfirm)) {
                    binding.textInputPassWordConfrimLayout.setError(getResources().getString(R.string.s_password_confrim_input_null_erro));
                    formatOK = false;
                } else if (!defaultPassWordConfirm.equals(binding.editPassWord.getText().toString())) {
                    binding.textInputPassWordConfrimLayout.setError(getResources().getString(R.string.s_password_confrim_input_inconsistent_erro));
                    formatOK = false;
                } else if (!TextUtils.isEmpty(defaultPassWordConfirm) && !RegexUtils.censorText(defaultPassWordConfirm, RegexPatternEnum.PASS_WORD)) {
                    binding.textInputPassWordConfrimLayout.setError(getResources().getString(R.string.s_password_input_type_erro));
                    formatOK = false;
                } else {
                    binding.textInputPassWordConfrimLayout.setErrorEnabled(false);
                }

                break;
        }
        return formatOK;
    }


    TextInputLayout.OnEndIconChangedListener onEndIconChangedListener = new TextInputLayout.OnEndIconChangedListener() {
        @Override
        public void onEndIconChanged(@NonNull TextInputLayout textInputLayout, int previousIcon) {
            int id = textInputLayout.getId();
            switch (id) {
                case R.id.textInputEmailLayout:
                    emailClean();
                    break;
            }
        }
    };

    private void emailClean() {
        binding.editEmail.setText("");
    }


    TextView.OnEditorActionListener onEmailEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String email = v.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    BaseBindingActivity.hideKeyboard(RegisterFragment.this);
                    boolean isEmailOk = RegexUtils.censorText(email, RegexPatternEnum.EMAIL);
                    if (!isEmailOk) {
                        binding.textInputEmailLayout.setError("無效的Email");
                    } else {
//                       binding.editPassWord.requestFocus();
                        binding.textInputEmailLayout.setErrorEnabled(false);
                        return true;
                    }
                    Log.v(TAG, "isEmailOk:" + isEmailOk);
                }
            }
            return false;
        }
    };

    TextWatcher emailTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setEmailTextChangeView(s, R.drawable.ic_baseline_cancel_24);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher passWordTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setTextChangeView(R.id.editPassWord, s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher passWordConfirmTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setTextChangeView(R.id.editPassWordConfirm, s);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void setTextChangeView(int editId, CharSequence s) {
        if (s.length() > 0) {
            checkFormat(editId);
        } else {
            checkFormat(editId);
        }
    }


    private void setEmailTextChangeView(CharSequence s, @DrawableRes int resId) {
        if (s.length() > 0) {
            if (!binding.textInputEmailLayout.isErrorEnabled()) {
                binding.textInputEmailLayout.setEndIconDrawable(MyApplication.getInstance().getDrawableRes(resId));
            }
            checkFormat(R.id.editEmail);
        } else {
            if (!binding.textInputEmailLayout.isErrorEnabled()) {
                binding.textInputEmailLayout.setEndIconDrawable(null);
            } else {
                checkFormat(R.id.editEmail);
            }
        }
    }

//    public void register(String userName, String passWord, String passWordRepeat) {
//        Log.v(TAG, "register ->");
//        Map<String, String> maps = new HashMap<>();
//        maps.put("userName", userName);
//        maps.put("passWord", passWord);
//        maps.put("passWordRepeat", passWordRepeat);
//        showLoading("請稍候..",false);
//        BaseApi.request(BaseApi.createApi(IServiceApi.class).register(maps), new BaseApi.IResponseListener<Account>() {
//            @Override
//            public void onSuccess(Account data) {
//                dismissLoading();
//                if (data.isResult()) {
//                    startEmailCodeFragment();
//                } else {
//                    showToast(data.getData().getErrMsg());
//                }
//                KLog.json(TAG + "register->", new Gson().toJson(data));
////                Log.v(TAG, "register() onSuccess -> data:" + data.getData() + ", isResult:" + data.isResult());
//            }
//
//            @Override
//            public void onFail() {
//                Log.v(TAG, "register() onFail ->");
//                dismissLoading();
//                showToast("請檢察網路是否連線中");
//            }
//
//            @Override
//            public void onError(Throwable error) {
//                dismissLoading();
//                showToast(error.getMessage());
//                Log.v(TAG, "register() onError -> error:" + error.getMessage() + ", code:" + error.toString());
//            }
//        });
//    }

    public void sendEmailCode(String userName, String passWord, String passWordRepeat) {
        Log.v(TAG, "sendEmailCode -> userName:" + userName +", passWord:" + passWord +", passWordRepeat:" + passWordRepeat);
        loginRegisterViewModel.sendEmailCode(userName, passWord, passWordRepeat).observe(requireActivity(), new Observer<BaseResponse<SendEmailCodeModel>>() {
            @Override
            public void onChanged(BaseResponse<SendEmailCodeModel> sendEmailCodeModelBaseResponse) {
                if(sendEmailCodeModelBaseResponse.isResult()){
                    startEmailCodeFragments();
                }
            }
        });

    }

    public void startEmailCodeFragments(){
        ApiRequest apiRequest = loginRegisterViewModel.getLoginRegisterRepository().getApiRequestByClassTag(new SendEmailCodeRequest().getTagName());
        if(apiRequest == null) return;
        LoginRegisterActivity loginRegisterActivity = ((LoginRegisterActivity)mActivity);
        loginRegisterActivity.startEmailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(apiRequest.getTagName(),apiRequest);
        loginRegisterActivity.navController.navigate(R.id.emailCodeFragment,bundle);
    }

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


}