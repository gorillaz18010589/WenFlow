package com.wen.flow.ui.email_code;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.databinding.FragmentEmailCodeBinding;
import com.wen.flow.support.base.BaseFragment;


public class EmailCodeFragment extends BaseFragment<FragmentEmailCodeBinding> {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                Log.v(MyApplication.TAG + TAG,"s:" + s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}