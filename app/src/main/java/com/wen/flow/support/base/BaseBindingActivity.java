package com.wen.flow.support.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.wen.flow.MainActivity;
import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.support.toast.TipsToast;
import com.wen.flow.support.util.LoadingUtils;
import com.wen.flow.support.util.StatusBarUtil;

public abstract class BaseBindingActivity<T extends ViewDataBinding> extends AppCompatActivity {
    protected String TAG = MyApplication.TAG +"->" +getClass().getSimpleName();
    protected T binding;
    protected FragmentManager fragmentManager;
    protected FragmentTransaction fragmentTransaction;
    private Fragment currentFragment = new Fragment();
    private LoadingUtils loadingUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        fragmentManager = getSupportFragmentManager();
        init();
    }

    protected abstract int getLayoutId();

    protected abstract void init();

    /*Fragment Start*/

    public void showFragment(@IdRes int containerViewId,Fragment fragment) {
        try {
            if (currentFragment != fragment) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(currentFragment);
//                LogUtils.d("showFragment -> hide:" + currentFragment.getClass().getSimpleName());
                currentFragment = fragment;
                if (!fragment.isAdded()) {
                    fragmentTransaction.add(containerViewId, fragment).show(fragment).commit();
                } else {
//                    LogUtils.d("showFragment -> currentFragment:" + currentFragment.getClass().getSimpleName());
                    fragmentTransaction.show(fragment).commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Fragment End*/

    /*CenterLoadingView Start*/

    public LoadingUtils getLoadingUtils() {
        if (loadingUtils == null) {
            loadingUtils = new LoadingUtils(this);
        }
        return loadingUtils;
    }

    public void showLoading(String txt, boolean canceledOnTouchOutside) {
        getLoadingUtils().getLoadingView().setCanceledOnTouchOutside(canceledOnTouchOutside);
        getLoadingUtils().show(txt);
    }

    public void showLoading(@StringRes int txtId, boolean canceledOnTouchOutside) {
        showLoading(getResources().getString(txtId), canceledOnTouchOutside);
    }

    public void dismissLoading() {
        getLoadingUtils().dismissLoading();
    }

    public void setLoadingImage(@DrawableRes int resId) {
        getLoadingUtils().getLoadingView().setLoadingImage(resId);
    }

    /*CenterLoadingView End*/


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


    public static void hideKeyboard(Fragment fragment) {
        FragmentActivity activity = fragment.requireActivity();
        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = fragment.getView();
            if (view != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            else if (fragment instanceof DialogFragment) {
                ((DialogFragment) fragment).getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        } else {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

}