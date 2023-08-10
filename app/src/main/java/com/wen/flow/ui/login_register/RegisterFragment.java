package com.wen.flow.ui.login_register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.databinding.FragmentRegisterBinding;
import com.wen.flow.enums.RegexPatternEnum;
import com.wen.flow.model.Account;
import com.wen.flow.network.BaseApi;
import com.wen.flow.network.IServiceApi;
import com.wen.flow.support.base.BaseBindingActivity;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.support.util.RegexUtils;

import java.util.HashMap;
import java.util.Map;

import static com.wen.flow.MyApplication.TAG;


public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {
    private int All_VIEW = -999;

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
            register(userName, passWord, passWordRepeat);
        } else {
            showToast(MyApplication.getInstance().getStringByRes(R.string.s_input_null_error));
            Log.v(TAG, "錯誤");
        }
    }

    @Override
    protected void initData() {

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

    public void register(String userName, String passWord, String passWordRepeat) {
        Log.v(TAG, "register ->");
        Map<String, String> maps = new HashMap<>();
        maps.put("userName", userName);
        maps.put("passWord", passWord);
        maps.put("passWordRepeat", passWordRepeat);
        BaseApi.request(BaseApi.createApi(IServiceApi.class).register(maps), new BaseApi.IResponseListener<Account>() {
            @Override
            public void onSuccess(Account data) {
                if (data.isResult()) {

                } else {
                    showToast(data.getData().getErrMsg());
                }
                Log.v(TAG, "register() onSuccess -> data:" + data.getData() + ", isResult:" + data.isResult());
            }

            @Override
            public void onFail() {
                Log.v(TAG, "register() onFail ->");
                showToast("請檢察網路是否連線中");
            }

            @Override
            public void onError(Throwable error) {
                showToast(error.getMessage());
                Log.v(TAG, "register() onError -> error:" + error.getMessage() + ", code:" + error.toString());
            }
        });
    }

}