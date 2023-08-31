package com.wen.flow.support.custom.titlebar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public interface ITitleBarStyle {

    /**
     * 創建標題視圖
     */
    TextView createTitleView(Context context);

    /**
     * 創建左標題視圖
     */
    TextView createLeftView(Context context);

    /**
     * 創建右標題視圖
     */
    TextView createRightView(Context context);

    /**
     * 創建分割線視圖
     */
    View createLineView(Context context);

    /**
     * 獲取標題欄背景
     */
    Drawable getTitleBarBackground(Context context);

    /**
     * 獲取左標題的按鈕背景
     */
    Drawable getLeftTitleBackground(Context context);

    /**
     * 獲取右標題的按鈕背景
     */
    Drawable getRightTitleBackground(Context context);

    /**
     * 獲取左標題的按鈕前景
     */
    Drawable getLeftTitleForeground(Context context);

    /**
     * 獲取右標題的按鈕前景
     */
    Drawable getRightTitleForeground(Context context);

    /**
     * 獲取返回按鈕的默認圖片
     */
    Drawable getBackButtonDrawable(Context context);

    /**
     * 獲取左標題子控件的水平內間距
     */
    int getLeftHorizontalPadding(Context context);

    /**
     * 獲取標題子控件的水平內間距
     */
    int getTitleHorizontalPadding(Context context);

    /**
     * 獲取右標題子控件的水平內間距
     */
    int getRightHorizontalPadding(Context context);

    /**
     * 獲取子控件的垂直內間距
     */
    int getChildVerticalPadding(Context context);

    /**
     * 獲取標題的默認文本
     */
    CharSequence getTitle(Context context);

    /**
     * 獲取左標題的默認文本
     */
    CharSequence getLeftTitle(Context context);

    /**
     * 獲取右邊標題的默認文本
     */
    CharSequence getRightTitle(Context context);

    /**
     * 獲取標題的默認字體顏色
     */
    ColorStateList getTitleColor(Context context);

    /**
     * 獲取左標題的默認字體顏色
     */
    ColorStateList getLeftTitleColor(Context context);

    /**
     * 獲取右邊標題的默認字體顏色
     */
    ColorStateList getRightTitleColor(Context context);

    /**
     * 獲取中間標題的默認字體大小
     */
    float getTitleSize(Context context);

    /**
     * 獲取左標題的默認字體大小
     */
    float getLeftTitleSize(Context context);

    /**
     * 獲取右邊標題的默認字體大小
     */
    float getRightTitleSize(Context context);

    /**
     * 獲取標題的字體樣式
     *
     * @param style         文字樣式
     *                      常規：{@link Typeface#NORMAL}
     *                      粗體：{@link Typeface#BOLD}
     *                      斜體：{@link Typeface#ITALIC}
     *                      粗斜體：{@link Typeface#BOLD_ITALIC}
     */
    Typeface getTitleTypeface(Context context, int style);

    /**
     * 獲取左標題的字體樣式
     *
     * @param style         文字樣式
     *                      常規：{@link Typeface#NORMAL}
     *                      粗體：{@link Typeface#BOLD}
     *                      斜體：{@link Typeface#ITALIC}
     *                      粗斜體：{@link Typeface#BOLD_ITALIC}
     */
    Typeface getLeftTitleTypeface(Context context, int style);

    /**
     * 獲取右邊標題的字體樣式
     *
     * @param style         文字樣式
     *                      常規：{@link Typeface#NORMAL}
     *                      粗體：{@link Typeface#BOLD}
     *                      斜體：{@link Typeface#ITALIC}
     *                      粗斜體：{@link Typeface#BOLD_ITALIC}
     */
    Typeface getRightTitleTypeface(Context context, int style);

    /**
     * 獲取標題的默認樣式
     */
    int getTitleStyle(Context context);

    /**
     * 獲取左標題的默認樣式
     */
    int getLeftTitleStyle(Context context);

    /**
     * 獲取右邊標題的默認樣式
     */
    int getRightTitleStyle(Context context);

    /**
     * 獲取標題的圖標默認重心
     */
    int getTitleIconGravity(Context context);

    /**
     * 獲取左標題的圖標默認重心
     */
    int getLeftIconGravity(Context context);

    /**
     * 獲取右邊標題的圖標默認重心
     */
    int getRightIconGravity(Context context);

    /**
     * 獲取標題的圖標默認間距
     */
    int getTitleIconPadding(Context context);

    /**
     * 獲取左標題的圖標默認間距
     */
    int getLeftIconPadding(Context context);

    /**
     * 獲取右邊標題的圖標默認間距
     */
    int getRightIconPadding(Context context);

    /**
     * 獲取標題的圖標默認寬度
     */
    int getTitleIconWidth(Context context);

    /**
     * 獲取左標題的圖標默認寬度
     */
    int getLeftIconWidth(Context context);

    /**
     * 獲取右邊標題的圖標默認寬度
     */
    int getRightIconWidth(Context context);

    /**
     * 獲取標題的圖標默認高度
     */
    int getTitleIconHeight(Context context);

    /**
     * 獲取左標題的圖標默認高度
     */
    int getLeftIconHeight(Context context);

    /**
     * 獲取右邊標題的圖標默認高度
     */
    int getRightIconHeight(Context context);

    /**
     * 獲取標題文本溢出的處理方式
     */
    TextUtils.TruncateAt getTitleOverflowMode(Context context);

    /**
     * 獲取左標題文本溢出的處理方式
     */
    TextUtils.TruncateAt getLeftTitleOverflowMode(Context context);

    /**
     * 獲取右邊標題文本溢出的處理方式
     */
    TextUtils.TruncateAt getRightTitleOverflowMode(Context context);

    /**
     * 分割線是否顯示
     */
    boolean isLineVisible(Context context);

    /**
     * 獲取分割線默認大小
     */
    int getLineSize(Context context);

    /**
     * 獲取分割線默認背景
     */
    Drawable getLineDrawable(Context context);
}

