package com.wen.flow.support.base;

import android.content.Context;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    protected Context context;
    protected T binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false);
        initView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListeners();
    }

    protected abstract int getLayoutResourceId();

    protected abstract void initView(View rootView);

    protected abstract void initData();

    protected abstract void initListeners();

    // 可選：如果需要在Fragment之間傳遞參數，可以添加一個靜態的newInstance方法
    // public static BaseFragment newInstance(String param1, int param2) {
    //     BaseFragment fragment = new BaseFragment();
    //     Bundle args = new Bundle();
    //     args.putString(ARG_PARAM1, param1);
    //     args.putInt(ARG_PARAM2, param2);
    //     fragment.setArguments(args);
    //     return fragment;
    // }

    // 可選：如果需要在Fragment中處理返回鍵事件，可以重寫onBackPressed方法
    // public void onBackPressed() {
    //     // 在此處理返回鍵事件
    // }
}