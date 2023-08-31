package com.wen.flow.support.custom.titlebar.style;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wen.flow.support.custom.titlebar.ITitleBarStyle;
import com.wen.flow.support.custom.titlebar.TitleBarSupport;


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/TitleBar
 *    time   : 2020/09/19
 *    desc   : 默认初始化器基类
 *
 *    有寫共用必須要的先寫好,因為是abstract,所以即使ITitleBarStyle接口實作,也不用全做出來
 *    而在CommonBarStyle有實做出來的方法,像createTitleView(),他就是變成一個方法
 *    abstract class的好處就是,我做好的方法你可以直接拿去用,
 *    而我要抽象(這邊就是指CommonBarStyle,沒做出來的ITitleBarStyle某些方法)的東西你子類別必須實作出來
 */
public abstract class CommonBarStyle implements ITitleBarStyle {

    @Override
    public TextView createTitleView(Context context) {
        TextView titleView = newTitleView(context);
        titleView.setGravity(Gravity.CENTER_VERTICAL);
        titleView.setFocusable(true);
        titleView.setSingleLine();
        return titleView;
    }

    public TextView newTitleView(Context context) {
        return new TextView(context);
    }

    @Override
    public TextView createLeftView(Context context) {
        TextView leftView = newLeftView(context);
        leftView.setGravity(Gravity.CENTER_VERTICAL);
        leftView.setFocusable(true);
        leftView.setSingleLine();
        return leftView;
    }

    public TextView newLeftView(Context context) {
        return new TextView(context);
    }

    @Override
    public TextView createRightView(Context context) {
        TextView rightView = newRightView(context);
        rightView.setGravity(Gravity.CENTER_VERTICAL);
        rightView.setFocusable(true);
        rightView.setSingleLine();
        return rightView;
    }

    public TextView newRightView(Context context) {
        return new TextView(context);
    }

    @Override
    public View createLineView(Context context) {
        return new View(context);
    }

    @Override
    public Drawable getLeftTitleForeground(Context context) {
        return null;
    }

    @Override
    public Drawable getRightTitleForeground(Context context) {
        return null;
    }

    @Override
    public int getLeftHorizontalPadding(Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getTitleHorizontalPadding(Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getRightHorizontalPadding(Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getChildVerticalPadding(Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());
    }

    @Override
    public CharSequence getTitle(Context context) {
        // 如果当前上下文对象是 Activity，就获取 Activity 的 label 属性作为标题栏的标题
        if (!(context instanceof Activity)) {
            return "";
        }

        // 获取清单文件中的 android:label 属性值
        CharSequence label = ((Activity) context).getTitle();
        if (TextUtils.isEmpty(label)) {
            return "";
        }

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            // 如果当前 Activity 没有设置 android:label 属性，则默认会返回 App 名称，则需要过滤掉
            if (!label.toString().equals(packageInfo.applicationInfo.loadLabel(packageManager).toString())) {
                // 设置标题
                return label;
            }
        } catch (PackageManager.NameNotFoundException ignored) {}

        return "";
    }

    @Override
    public CharSequence getLeftTitle(Context context) {
        return "";
    }

    @Override
    public CharSequence getRightTitle(Context context) {
        return "";
    }

    @Override
    public float getTitleSize(Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
    }

    @Override
    public float getLeftTitleSize(Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics());
    }

    @Override
    public float getRightTitleSize(Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics());
    }

    @Override
    public Typeface getTitleTypeface(Context context, int style) {
        return TitleBarSupport.getTextTypeface(style);
    }

    @Override
    public Typeface getLeftTitleTypeface(Context context, int style) {
        return TitleBarSupport.getTextTypeface(style);
    }

    @Override
    public Typeface getRightTitleTypeface(Context context, int style) {
        return TitleBarSupport.getTextTypeface(style);
    }

    @Override
    public int getTitleStyle(Context context) {
        return Typeface.NORMAL;
    }

    @Override
    public int getLeftTitleStyle(Context context) {
        return Typeface.NORMAL;
    }

    @Override
    public int getRightTitleStyle(Context context) {
        return Typeface.NORMAL;
    }

    @Override
    public int getTitleIconGravity(Context context) {
        return Gravity.END;
    }

    @Override
    public int getLeftIconGravity(Context context) {
        return Gravity.START;
    }

    @Override
    public int getRightIconGravity(Context context) {
        return Gravity.END;
    }

    @Override
    public int getTitleIconPadding(Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getLeftIconPadding(Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getRightIconPadding(Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics());
    }

    @Override
    public int getTitleIconWidth(Context context) {
        return 0;
    }

    @Override
    public int getLeftIconWidth(Context context) {
        return 0;
    }

    @Override
    public int getRightIconWidth(Context context) {
        return 0;
    }

    @Override
    public int getTitleIconHeight(Context context) {
        return 0;
    }

    @Override
    public int getLeftIconHeight(Context context) {
        return 0;
    }

    @Override
    public int getRightIconHeight(Context context) {
        return 0;
    }

    @Override
    public TextUtils.TruncateAt getTitleOverflowMode(Context context) {
        return TextUtils.TruncateAt.MARQUEE;
    }

    @Override
    public TextUtils.TruncateAt getLeftTitleOverflowMode(Context context) {
        return null;
    }

    @Override
    public TextUtils.TruncateAt getRightTitleOverflowMode(Context context) {
        return null;
    }

    @Override
    public boolean isLineVisible(Context context) {
        return true;
    }

    @Override
    public int getLineSize(Context context) {
        return 1;
    }
}