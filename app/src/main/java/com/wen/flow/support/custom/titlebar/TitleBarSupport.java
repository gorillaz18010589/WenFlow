package com.wen.flow.support.custom.titlebar;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public final class TitleBarSupport {

    /** 無色值 */
    static final int NO_COLOR = Color.TRANSPARENT;

    public static final int ELLIPSIZE_NONE = 0;
    public static final int ELLIPSIZE_START = 1;
    public static final int ELLIPSIZE_MIDDLE = 2;
    public static final int ELLIPSIZE_END = 3;
    public static final int ELLIPSIZE_MARQUEE = 4;

    /**
     * 獲取圖片資源
     */
    public static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, context.getTheme());
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    /**
     * 設置 View 背景
     */
    public static void setBackground(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    /**
     * 設置 View 前景
     */
    public static void setForeground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setForeground(drawable);
        }
    }

    /**
     * 獲取絕對重心
     */
    public static int getAbsoluteGravity(View view, int gravity) {
        // 適配布局反方向
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return gravity;
        }
        return Gravity.getAbsoluteGravity(gravity, view.getResources().getConfiguration().getLayoutDirection());
    }

    /**
     * 是否啟用了布局反方向特性
     */
    public static boolean isLayoutRtl(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return false;
        }

        return context.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    /**
     * TextView 是否存在內容
     */
    public static boolean containContent(TextView textView) {
        CharSequence text = textView.getText();
        if (!TextUtils.isEmpty(text)) {
            return true;
        }
        Drawable[] drawables = textView.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 給圖片設置著色器
     */
    public static void setDrawableTint(Drawable drawable, int color) {
        if (drawable == null) {
            return;
        }
        if (color == NO_COLOR) {
            return;
        }
        drawable.mutate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_IN));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    /**
     * 清除圖片設置著色器
     */
    public static void clearDrawableTint(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        drawable.mutate();
        drawable.clearColorFilter();
    }

    /**
     * 根據給定的大小限制 Drawable 寬高
     */
    public static void setDrawableSize(Drawable drawable, int width, int height) {
        if (drawable == null ) {
            return;
        }

        if (width <= 0 && height <= 0) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return;
        }

        if (width > 0 && height > 0) {
            drawable.setBounds(0, 0, width, height);
            return;
        }

        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        if (drawableWidth <= 0) {
            drawableWidth = width;
        }
        if (drawableHeight <= 0) {
            drawableHeight = height;
        }

        // 將 Drawable 等比縮放
        if (width > 0) {
            drawable.setBounds(0, 0, width, width * drawableHeight / drawableWidth);
        } else {
            drawable.setBounds(0, 0, drawableWidth * height / drawableHeight, height);
        }
    }

    /**
     * 根據圖標重心獲取在 TextView 的 Drawable 對象
     */
    public static Drawable getTextCompoundDrawable(TextView textView, int gravity) {
        Drawable[] drawables = textView.getCompoundDrawables();
        switch (getAbsoluteGravity(textView, gravity)) {
            case Gravity.LEFT:
                return drawables[0];
            case Gravity.TOP:
                return drawables[1];
            case Gravity.RIGHT:
                return drawables[2];
            case Gravity.BOTTOM:
                return drawables[3];
            default:
                return null;
        }
    }

    /**
     * 根據圖標重心設置 TextView 某個位置的 Drawable
     */
    public static void setTextCompoundDrawable(TextView textView, Drawable drawable, int gravity) {
        switch (getAbsoluteGravity(textView, gravity)) {
            case Gravity.LEFT:
                textView.setCompoundDrawables(drawable, null, null, null);
                break;
            case Gravity.TOP:
                textView.setCompoundDrawables(null, drawable, null, null);
                break;
            case Gravity.RIGHT:
                textView.setCompoundDrawables(null, null, drawable, null);
                break;
            case Gravity.BOTTOM:
                textView.setCompoundDrawables(null, null, null, drawable);
                break;
            default:
                textView.setCompoundDrawables(null, null, null, null);
                break;
        }
    }

    /**
     * 根據文字樣式返回不同的字體樣式
     */
    public static Typeface getTextTypeface(int style) {
        switch (style) {
            case Typeface.BOLD:
                return Typeface.DEFAULT_BOLD;
            case Typeface.ITALIC:
            case Typeface.BOLD_ITALIC:
                return Typeface.MONOSPACE;
            case Typeface.NORMAL:
            default:
                return Typeface.DEFAULT;
        }
    }

    /**
     * int 轉 TruncateAt 枚舉
     */
    public static TextUtils.TruncateAt convertIntToTruncateAtEnum(int ellipsize) {
        switch (ellipsize) {
            case ELLIPSIZE_START:
                return TextUtils.TruncateAt.START;
            case ELLIPSIZE_MIDDLE:
                return TextUtils.TruncateAt.MIDDLE;
            case ELLIPSIZE_END:
                return TextUtils.TruncateAt.END;
            case ELLIPSIZE_MARQUEE:
                return TextUtils.TruncateAt.MARQUEE;
            default:
                return null;
        }
    }

    /**
     * 設置 TextView 文本溢出處理方式
     */
    public static void setTextViewEllipsize(TextView textView, TextUtils.TruncateAt where) {
        if (textView.getEllipsize() == where) {
            return;
        }
        textView.setEllipsize(where);
        if (where != TextUtils.TruncateAt.MARQUEE) {
            return;
        }
        if (!textView.isSelected()) {
            // 設置跑馬燈之後需要設置選中才能有效果
            textView.setSelected(true);
        }
        if (!textView.isFocusable()) {
            // 設置跑馬燈需要先獲取焦點
            textView.setFocusable(true);
        }
        // 設置跑馬燈的循環次數
        textView.setMarqueeRepeatLimit(-1);
    }
}
