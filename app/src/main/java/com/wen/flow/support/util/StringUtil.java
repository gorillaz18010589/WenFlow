package com.wen.flow.support.util;

import androidx.annotation.StringRes;

import com.wen.flow.MyApplication;

public class StringUtil {
    public static String getString(@StringRes int resId) {
        return MyApplication.getInstance().getString(resId);
    }

}
