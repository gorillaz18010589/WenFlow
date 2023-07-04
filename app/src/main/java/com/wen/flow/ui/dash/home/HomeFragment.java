package com.wen.flow.ui.dash.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wen.flow.R;
import com.wen.flow.databinding.FragmentHomeBinding;
import com.wen.flow.support.base.BaseFragment;
import com.wen.flow.support.custom_view.CustomConstraintLayout;


public class HomeFragment extends BaseFragment<FragmentHomeBinding> {



    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
//        CustomConstraintLayout customLayout = new CustomConstraintLayout(getContext());
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(300, 300);
//        customLayout.setLayoutParams(layoutParams);
//
//        customLayout.setStartColor(Color.parseColor("#002C5C"));
//        customLayout.setMiddleColor(Color.parseColor("#0051A0"));
//        customLayout.setEndColor(Color.parseColor("#0095D1"));
//        customLayout.setCornerRadiusTopLeft(20);
//        customLayout.setCornerRadiusBottomRight(20);
//        customLayout.setShadowRadius(6);
//        customLayout.setShadowColor(Color.BLACK);
//        customLayout.setShadowDx(0);
//        customLayout.setShadowDy(2);


    }

    @Override
    protected void initListeners() {

    }
}