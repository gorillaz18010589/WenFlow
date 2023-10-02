package com.wen.flow.ui.dash.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wen.flow.R;
import com.wen.flow.databinding.FragmentUserBinding;
import com.wen.flow.network.response.LoginModel;
import com.wen.flow.support.base.BaseFragment;


public class UserFragment extends BaseFragment<FragmentUserBinding> {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }

    public void updateUser(LoginModel loginModel) {
        binding.userName.setText(loginModel.getUserName());
    }

    public UserFragment getUserFragment() {
        return UserFragment.this;
    }
}