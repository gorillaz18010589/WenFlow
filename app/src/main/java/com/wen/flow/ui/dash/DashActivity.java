package com.wen.flow.ui.dash;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.wen.flow.MainActivity;
import com.wen.flow.R;
import com.wen.flow.databinding.ActivityDashBinding;
import com.wen.flow.support.base.BaseBindingActivity;
import com.wen.flow.ui.dash.home.HomeFragment;
import com.wen.flow.ui.dash.user.UserFragment;
import com.wen.flow.ui.login_register.LoginRegisterActivity;

public class DashActivity extends BaseBindingActivity<ActivityDashBinding> {
    private int btnTestClickCount =0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dash;
    }

    @Override
    protected void init() {
        Log.v("hank", "Dash");
        initFragment();
//        showToast();
        binding.btnTest.setOnClickListener(v ->{
            btnTestClickCount++;
//            TipsToast.getInstance(MyApplication.getInstance(),R.drawable.ic_action_name).showTips("按了" + btnTestClickCount +"次");
//            showToast("測試一下",R.drawable.ic_action_name);
            showSuccessTips("測試ffewfewjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjfewfewfwefwefwefewf");
            
        });

        startActivity(new Intent(DashActivity.this, LoginRegisterActivity.class));

    }

    private void initFragment() {
        UserFragment userFragment = new UserFragment();
        HomeFragment homeFragment = new HomeFragment();
        showFragment(R.id.fragmentContainerView,userFragment);
        showFragment(R.id.fragmentContainerView,homeFragment);
    }



}
