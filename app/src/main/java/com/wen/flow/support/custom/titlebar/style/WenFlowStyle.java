package com.wen.flow.support.custom.titlebar.style;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.wen.flow.R;
import com.wen.flow.support.custom.titlebar.TitleBarSupport;


public class WenFlowStyle extends CommonBarStyle{
    @Override
    public Drawable getTitleBarBackground(Context context) {
        return new ColorDrawable(0xFFFFFFFF);    }

    @Override
    public Drawable getLeftTitleBackground(Context context) {
        return null;
    }

    @Override
    public Drawable getRightTitleBackground(Context context) {
        return null;
    }

    @Override
    public Drawable getBackButtonDrawable(Context context) {
        return TitleBarSupport.getDrawable(context, R.drawable.bar_arrows_left_black);
    }

    @Override
    public ColorStateList getTitleColor(Context context) {
        return ColorStateList.valueOf(0xFF222222);
    }

    @Override
    public ColorStateList getLeftTitleColor(Context context) {
        return ColorStateList.valueOf(0xFF03A9F4);//必須要FF否則顯示不出來
//        return ColorStateList.valueOf(0xFF00FF00);
//        return ColorStateList.valueOf(0xFF00FF00);
    }

    @Override
    public ColorStateList getRightTitleColor(Context context) {
        return ColorStateList.valueOf(0xFFE91E63);
    }

    @Override
    public Drawable getLineDrawable(Context context) {
        return new ColorDrawable(0xFFECECEC);
    }
}
