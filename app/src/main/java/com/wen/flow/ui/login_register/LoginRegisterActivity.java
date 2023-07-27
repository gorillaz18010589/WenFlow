package com.wen.flow.ui.login_register;

import androidx.annotation.Nullable;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.wen.flow.R;
import com.wen.flow.databinding.ActivityLoginRegisterBinding;
import com.wen.flow.enums.TitleCloseBarEnum;
import com.wen.flow.support.base.BaseBindingActivity;
import com.wen.flow.enums.IconEnum;
import com.wen.flow.viewmodel.ImageButtonViewModel;


public class LoginRegisterActivity extends BaseBindingActivity<ActivityLoginRegisterBinding> {
    private ImageButtonViewModel imageButtonViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_register;
    }

    @Override
    protected void init() {
        binding.includeTitleCloseBar.setTitleCloseBarEnum(TitleCloseBarEnum.Login);
        binding.includeTitleCloseBar.setIconEnum(IconEnum.ICON_CLOSE);
        binding.includeTitleCloseBar.shapeImageBtnCancel.setOnClickListener(v ->{
            showToast("按到");
        });
        binding.toggleButtonGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(checkedId == R.id.mBtn1){
                    showToast("mBtn1");
                }else {
                    showToast("mBtn2");
                }
            }
        });
        Log.v("hank","init()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("hank","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("hank","onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("hank","onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("hank","onRestart()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.v("hank","onCreate()");
    }
}