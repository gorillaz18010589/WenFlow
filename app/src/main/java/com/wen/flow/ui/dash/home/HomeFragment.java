package com.wen.flow.ui.dash.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wen.flow.R;
import com.wen.flow.databinding.FragmentHomeBinding;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.support.base.BaseFragment;


public class HomeFragment extends BaseFragment<FragmentHomeBinding> {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.v("生命週期 onCreate()");
    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.v("生命週期 onResume()");
    }

    @Override
    public void onStart() {
        super.onStart();
        KLog.v("生命週期 onStart()");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KLog.v("生命週期 onViewCreated()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        KLog.v("生命週期 onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.v("生命週期 onDestroy()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KLog.v("生命週期 onDestroyView()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        KLog.v("生命週期 onAttach()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        KLog.v("生命週期 onDetach()");
    }

    @Override
    public void onStop() {
        super.onStop();
        KLog.v("生命週期 onStop()");
    }

    @Override
    public void onPause() {
        super.onPause();
        KLog.v("生命週期 onPause()");
    }

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