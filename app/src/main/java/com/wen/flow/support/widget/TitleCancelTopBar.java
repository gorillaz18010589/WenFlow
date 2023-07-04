package com.wen.flow.support.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.wen.flow.R;
import com.wen.flow.support.custom_view.CustomConstraintLayout;

public class TitleCancelTopBar extends ConstraintLayout {
    private String TAG = getClass().getSimpleName() +"->";
    private CustomConstraintLayout topBar;

    public TitleCancelTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v("hank","TitleCancelTopBar(Context context, AttributeSet attrs)");
        View view = LayoutInflater.from(context).inflate(R.layout.widget_title_cancel_top_bar,this,false);
//        topBar = view.findViewById(R.id.topBar);

    }

    private void init() {
//        CustomConstraintLayout customLayout = new CustomConstraintLayout(getContext());
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(300, 300);
//        this.setLayoutParams(layoutParams);
//
//        setStartColor(Color.parseColor("#002C5C"));
//        setMiddleColor(Color.parseColor("#0051A0"));
//        setEndColor(Color.parseColor("#0095D1"));
//        setCornerRadiusTopLeft(20);
//        setCornerRadiusBottomRight(20);
//        setShadowRadius(6);
//        setShadowColor(Color.BLACK);
//        setShadowDx(0);
//        setShadowDy(2);
    }


}
