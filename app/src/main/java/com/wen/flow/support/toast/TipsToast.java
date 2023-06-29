package com.wen.flow.support.toast;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.databinding.WidgetTipsToastBinding;


public class TipsToast {
    private static Toast toast;
    private static MyApplication mContext;
    private static Handler mToastHandler;
    private static WidgetTipsToastBinding mBinding;
    private static TipsToast instance;
    private  static int defaultIcon = 0;

    public static TipsToast getInstance(MyApplication application, @DrawableRes int drawableId) {
        if (instance == null) {
            instance = new TipsToast(application, drawableId);
        }
        return instance;
    }

    public static TipsToast getInstance(MyApplication application) {
        if (instance == null) {
            instance = new TipsToast(application);
        }
        return instance;
    }

    public TipsToast(MyApplication application) {
        mContext = application;
        mToastHandler = new Handler(Looper.myLooper());
        mBinding = WidgetTipsToastBinding.inflate(LayoutInflater.from(mContext));
    }

    public TipsToast(MyApplication application, @DrawableRes int drawableId) {
        this(application);
        defaultIcon = drawableId;
    }

    public void showTips(@StringRes int stringId) {
        String msg = mContext.getString(stringId);
        showTipsImpl(msg, Toast.LENGTH_SHORT, defaultIcon);
    }

    public void showTips(String msg) {
        showTipsImpl(msg, Toast.LENGTH_SHORT, defaultIcon);
    }

    public void showTips(String msg, int duration) {
        showTipsImpl(msg, duration, defaultIcon);
    }

    public void showTips(@StringRes int stringId, int duration) {
        String msg = mContext.getString(stringId);
        showTipsImpl(msg, duration, defaultIcon);
    }

    public void showSuccessTips(String msg) {
        showTipsImpl(msg, Toast.LENGTH_SHORT, R.drawable.widget_toast_success);
    }

    public void showSuccessTips(@StringRes int stringId) {
        String msg = mContext.getString(stringId);
        showTipsImpl(msg, Toast.LENGTH_SHORT, R.drawable.widget_toast_success);
    }

    public void showWarningTips(String msg) {
        showTipsImpl(msg, Toast.LENGTH_SHORT, R.drawable.widget_toast_warning);
    }

    public void showWarningTips(@StringRes int stringId) {
        String msg = mContext.getString(stringId);
        showTipsImpl(msg, Toast.LENGTH_SHORT, R.drawable.widget_toast_warning);
    }

    private void showTipsImpl(String msg, int duration, @DrawableRes int drawableId) {
        if (msg == null || msg.isEmpty()) {
            return;
        }
        if (toast != null) {
            cancel();
            toast = null;
        }
        mToastHandler.postDelayed(() -> {
            try {
                toast = new Toast(mContext);
                toast.setView(mBinding.getRoot());
                mBinding.tipToastTxt.setText(msg);
                mBinding.tipToastTxt.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(duration);
                toast.show();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("show tips error", e.toString());
            }
        }, 50);
    }

    public void cancel() {
        if (toast != null) {
            toast.cancel();
        }
        if (mToastHandler != null) {
            mToastHandler.removeCallbacksAndMessages(null);
        }
    }
}



