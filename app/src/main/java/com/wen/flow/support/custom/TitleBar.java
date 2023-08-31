package com.wen.flow.support.custom;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wen.flow.R;
import com.wen.flow.databinding.LayoutTitleBarBinding;
import com.wen.flow.framework.manager.app.ActivityManager;

//public class TitleBar extends FrameLayout {
//
//    private LayoutTitleBarBinding mBinding;
//
//    public TitleBar(Context context) {
//        this(context, null);
//    }
//
//    public TitleBar(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context, attrs);
//    }
//
//    private void init(Context context, AttributeSet attrs) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
//        mBinding = LayoutTitleBarBinding.inflate(inflater, this, true);
//
//        mBinding.llBackLayer.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Activity activity = (Activity) context;
//                if (ActivityManager.isActivityDestroy(context)) {
//                    return;
//                }
//                activity.finish();
//            }
//        });
//
//        setAttrs(attrs, array);
//    }
//
//    private void setAttrs(AttributeSet attrs, TypedArray array) {
//        if (attrs == null) {
//            return;
//        }
//        int count = array.getIndexCount();
//        for (int i = 0; i < count; i++) {
//            int attr = array.getIndex(i);
//            switch (attr) {
//                case R.styleable.TitleBar_leftText:
//                    String leftText = array.getString(attr);
//                    mBinding.tvLeft.setText(leftText);
//                    break;
//                case R.styleable.TitleBar_middleText:
//                    String middleText = array.getString(attr);
//                    mBinding.tvMiddle.setText(middleText);
//                    break;
//                case R.styleable.TitleBar_rightText:
//                    String rightText = array.getString(attr);
//                    mBinding.tvRight.setText(rightText);
//                    break;
//                case R.styleable.TitleBar_leftIcon:
//                    Drawable leftIcon = array.getDrawable(attr);
//                    mBinding.ivLeftIcon.setImageDrawable(leftIcon);
//                    break;
//                case R.styleable.TitleBar_rightIcon:
//                    Drawable rightIcon = array.getDrawable(attr);
//                    mBinding.rightIvIcon.setImageDrawable(rightIcon);
//                    break;
//                case R.styleable.TitleBar_middleTextColor:
//                    int middleTextColor = array.getColor(attr, 0);
//                    mBinding.tvMiddle.setTextColor(middleTextColor);
//                    break;
//                case R.styleable.TitleBar_leftTextColor:
//                    int leftTextColor = array.getColor(attr, 0);
//                    mBinding.tvLeft.setTextColor(leftTextColor);
//                    break;
//                case R.styleable.TitleBar_rightTextColor:
//                    int rightTextColor = array.getColor(attr, 0);
//                    mBinding.tvRight.setTextColor(rightTextColor);
//                    break;
//                case R.styleable.TitleBar_background:
//                    int backgroundColor = array.getColor(attr, 0);
//                    mBinding.root.setBackgroundColor(backgroundColor);
//                    break;
//                case R.styleable.TitleBar_leftTextSize:
//                    float leftTextSize = array.getDimension(attr, 0f);
//                    mBinding.tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
//                    break;
//                case R.styleable.TitleBar_rightTextSize:
//                    float rightTextSize = array.getDimension(attr, 0f);
//                    mBinding.tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
//                    break;
//                case R.styleable.TitleBar_leftTextBold:
//                    boolean leftTextBold = array.getBoolean(attr, false);
//                    TextPaint leftPaint = mBinding.tvLeft.getPaint();
//                    leftPaint.setFakeBoldText(leftTextBold);
//                    break;
//                case R.styleable.TitleBar_middleTextBold:
//                    boolean middleTextBold = array.getBoolean(attr, false);
//                    TextPaint middlePaint = mBinding.tvMiddle.getPaint();
//                    middlePaint.setFakeBoldText(middleTextBold);
//                    break;
//                case R.styleable.TitleBar_rightTextBold:
//                    boolean rightTextBold = array.getBoolean(attr, false);
//                    TextPaint rightPaint = mBinding.tvRight.getPaint();
//                    rightPaint.setFakeBoldText(rightTextBold);
//                    break;
//                case R.styleable.TitleBar_leftVisible:
//                    boolean leftVisible = array.getBoolean(attr, true);
//                    int leftVisibility = leftVisible ? VISIBLE : INVISIBLE;
//                    mBinding.llBackLayer.setVisibility(leftVisibility);
//                    mBinding.tvLeft.setVisibility(leftVisibility);
//                    break;
//                case R.styleable.TitleBar_rightVisible:
//                    boolean rightVisible = array.getBoolean(attr, true);
//                    int rightVisibility = rightVisible ? VISIBLE : INVISIBLE;
//                    mBinding.llRightLayer.setVisibility(rightVisibility);
//                    mBinding.tvRight.setVisibility(rightVisibility);
//                    break;
//                case R.styleable.TitleBar_middleTextVisible:
//                    boolean middleTextVisible = array.getBoolean(attr, false);
//                    int middleTextVisibility = middleTextVisible ? VISIBLE : GONE;
//                    mBinding.tvMiddle.setVisibility(middleTextVisibility);
//                    break;
//                case R.styleable.TitleBar_rightIconVisible:
//                    boolean rightIconVisible = array.getBoolean(attr, false);
//                    int rightIconVisibility = rightIconVisible ? VISIBLE : GONE;
//                    mBinding.rightIvIcon.setVisibility(rightIconVisibility);
//                    break;
//                case R.styleable.TitleBar_showDividerLine:
//                    boolean showDividerLine = array.getBoolean(attr, false);
//                    int dividerLineVisibility = showDividerLine ? VISIBLE : GONE;
//                    mBinding.dividerLine.setVisibility(dividerLineVisibility);
//                    break;
//            }
//        }
//        array.recycle();
//    }
//
//    public void setLeftText(String title) {
//        mBinding.tvLeft.setText(title);
//    }
//
//    public void setMiddleText(String title) {
//        mBinding.tvMiddle.setText(title);
//    }
//
//    public void setMiddleText(int resId) {
//        mBinding.tvMiddle.setText(resId);
//    }
//
//    public ConstraintLayout getTitleRootView() {
//        return mBinding.root;
//    }
//
//    public TextView getLeftTextView() {
//        return mBinding.tvLeft;
//    }
//
//    public TextView getMiddleTextView() {
//        return mBinding.tvMiddle;
//    }
//
//    public TextView getRightTextView() {
//        return mBinding.tvRight;
//    }
//}