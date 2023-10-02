package com.wen.flow.ui.dash.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.framework.log.KLog;
import com.yc.cn.ycbannerlib.banner.inter.BaseHintView;

public class TextRectangleHintView extends AppCompatTextView implements BaseHintView {
    private int length;

    public TextRectangleHintView(@NonNull Context context) {
        super(context);
    }

    public TextRectangleHintView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void initView(int length, int gravity) {
        this.length = length;
        KLog.v(gravity);
        setTextColor(Color.BLACK);
//        setBackground(MyApplication.getInstance().getDrawableRes(R.drawable.rounded_text_background));
//        switch (gravity) {
//            case 0:
//                setGravity(Gravity.START| Gravity.CENTER_VERTICAL);
//                break;
//            case 1:
//                setGravity(Gravity.CENTER);
//                break;
//            case 2:
//                setGravity(Gravity.END| Gravity.CENTER_VERTICAL);
//                break;
//            default:
//                break;
//        }
        setGravity(Gravity.END| Gravity.CENTER_VERTICAL);
        setCurrent(0);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setCurrent(int current) {
        setText(current+1+"/"+ length);
    }
}


//public class TextRectangleHintView extends AppCompatTextView implements BaseHintView {
//    private int length;
//
//    public TextRectangleHintView(@NonNull Context context) {
//        super(context);
//    }
//
//    public TextRectangleHintView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//    @Override
//    public void initView(int length, int gravity) {
//        this.length = length;
//        KLog.v(gravity);
//        setTextColor(Color.WHITE);
////        switch (gravity) {
////            case 0:
////                setGravity(Gravity.START| Gravity.CENTER_VERTICAL);
////                break;
////            case 1:
////                setGravity(Gravity.CENTER);
////                break;
////            case 2:
////                setGravity(Gravity.END| Gravity.CENTER_VERTICAL);
////                break;
////            default:
////                break;
////        }
//        setGravity(Gravity.END| Gravity.CENTER_VERTICAL);
//        setCurrent(0);
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void setCurrent(int current) {
//        setText(current+1+"/"+ length);
//    }
//}
