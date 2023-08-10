package com.wen.flow.support.base;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.support.toast.TipsToast;
import com.wen.flow.support.util.LoadingUtils;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    protected Context context;
    protected Activity mActivity;
    protected T binding;
    protected boolean mIsViewCreate = false;
    private LoadingUtils loadingUtils;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof Activity) {
            mActivity = (Activity) context;
//            LogUtils.d("當前acitivty:" + mActivity.getClass().getSimpleName());
        }
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
        mIsViewCreate = true;
        initData();
        initListeners();
    }

    protected abstract int getLayoutResourceId();

    protected abstract void initView(View rootView);

    protected abstract void initData();

    protected abstract void initListeners();

    public void showLoading(String txt, boolean canceledOnTouchOutside) {
        getLoadingUtils().getLoadingView().setCanceledOnTouchOutside(canceledOnTouchOutside);
        getLoadingUtils().show(txt);
    }

    public void showLoading(@StringRes int txtId, boolean canceledOnTouchOutside) {
        showLoading(getResources().getString(txtId), canceledOnTouchOutside);
    }
    protected void showLoading(String msg) {
        getLoadingUtils().show(msg);
    }

    protected void showLoading(@StringRes int res) {
        showLoading(getString(res));
    }

    protected void dismissLoading() {
        getLoadingUtils().dismissLoading();
    }

    /*ToastUtils Start*/

    public void showToast(String msg) {
        showToast(msg, 0);
    }

    public void showToast(@StringRes int stringId) {
        showToast(stringId, 0);
    }

    public void showToast(String msg, @DrawableRes int drawableId) {
        TipsToast.getInstance(MyApplication.getInstance(), drawableId).showTips(msg);
    }

    public void showToast(@StringRes int stringId, @DrawableRes int drawableId) {
        TipsToast.getInstance(MyApplication.getInstance(), drawableId).showTips(stringId);
    }

    public void showSuccessTips(@StringRes int stringId) {
        TipsToast.getInstance(MyApplication.getInstance()).showSuccessTips(stringId);
    }

    public void showSuccessTips(String msg) {
        TipsToast.getInstance(MyApplication.getInstance()).showSuccessTips(msg);
    }

    public void showWarningTips(@StringRes int stringId) {
        TipsToast.getInstance(MyApplication.getInstance()).showWarningTips(stringId);
    }

    public void showWarningTips(String msg) {
        TipsToast.getInstance(MyApplication.getInstance()).showWarningTips(msg);
    }


    /*ToastUtils End*/
    public LoadingUtils getLoadingUtils() {
        if (loadingUtils == null) {
            loadingUtils = new LoadingUtils(mActivity);
        }
        return loadingUtils;
    }


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