package com.wen.flow.ui.email_code;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.databinding.FragmentEmailCodeBinding;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.framework.navigation.navigator.ReplaceFragment;
import com.wen.flow.model.Account;
import com.wen.flow.network.response.BaseResponse;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.ui.login_register.LoginRegisterViewModel;

@ReplaceFragment
public class EmailCodeFragment extends BaseFragment<FragmentEmailCodeBinding> {


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
    }
}