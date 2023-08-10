package com.wen.flow.ui.login_register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.wen.flow.MyApplication;
import com.wen.flow.R;
import com.wen.flow.databinding.ActivityLoginRegisterBinding;
import com.wen.flow.enums.TitleCloseBarEnum;
import com.wen.flow.framework.manager.app.ActivityManager;
import com.wen.flow.framework.manager.app.AppManager;
import com.wen.flow.support.base.BaseBindingActivity;
import com.wen.flow.enums.IconEnum;
import com.wen.flow.support.util.DeviceInfoUtils;
import com.wen.flow.viewmodel.ImageButtonViewModel;

import java.util.Iterator;
import java.util.List;


public class LoginRegisterActivity extends BaseBindingActivity<ActivityLoginRegisterBinding> {
    private ImageButtonViewModel imageButtonViewModel;
    private LoginFragment loginFragment = new LoginFragment();
    private RegisterFragment registerFragment = new RegisterFragment();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_register;
    }



    @Override
    protected void init() {
        addFragment();
        binding.includeTitleCloseBar.setTitleCloseBarEnum(TitleCloseBarEnum.Login);
        binding.includeTitleCloseBar.setIconEnum(IconEnum.ICON_CLOSE);
        binding.includeTitleCloseBar.shapeImageBtnCancel.setOnClickListener(v ->{
//            showToast("按到");
//            getAllActivityInStack();
//            getAppInformation();
            getAppDeviceInformation();
        });

        binding.toggleButtonGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(checkedId == R.id.mBtn1){
                    showToast("mBtn1");
                    changeFragment(loginFragment,"LOGIN");
                }else {
                    showToast("mBtn2");
                    changeFragment(registerFragment,"REGISTER");
                }
            }
        });
        Log.v("hank","init()");

//        testMotinlayout();
    }

    private void getAppInformation() {
        Log.v(TAG,new AppManager().toString());
    }



    private void getAppDeviceInformation(){
//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
//                .subscribe(g ->{
//                    if(g){
//                        showToast("允許");
//                    }else {
//                        showToast("沒有允許");
//                    }
//                });
        DeviceInfoUtils.init(this,this,true);
        String phoneDevice = DeviceInfoUtils.getPhoneDevice();
        String phoneBrand = DeviceInfoUtils.getPhoneBrand();
        String phoneManufacturer = DeviceInfoUtils.getPhoneManufacturer();
        String phoneModel = DeviceInfoUtils.getPhoneModel();
        String androidId = DeviceInfoUtils.getAndroidId();
        String imei = DeviceInfoUtils.getImei();
        String wifiSSID = DeviceInfoUtils.getWifiSSID();
        String mac = DeviceInfoUtils.getMac();

        Log.v(TAG,String.format("" +
                "\n phoneDevice:%s\n" +
                " phoneBrand:%s\n" +
                " phoneManufacturer:%s\n" +
                " phoneModel:%s\n" +
                " androidId:%s\n" +
                " imei:%s\n" +
                " wifiSSID:%s\n" +
                " mac:%s\n"
                ,phoneDevice, phoneBrand,phoneManufacturer,phoneModel,androidId,imei,wifiSSID,mac));
//        Log.v(TAG,"getAppDeviceInformation() " + String.format(
//                "phoneDevice:%s",
//                "phoneBrand:%s",
//                "phoneManufacturer:%s",
//                "phoneModel:%s",
//                "androidId:%s",
//                "imei:%s",
//                "mac:%s",
//                "wifiSSID:%s"
//                ,phoneDevice,phoneBrand,phoneManufacturer,phoneModel,androidId,imei,mac,wifiSSID));
    }

    private void getAllActivityInStack() {
        List<Activity> activities = ActivityManager.getTasksActivity();
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()){
            Activity activity = iterator.next();
            String activityName = activity.getLocalClassName();
            Log.v("hank","目前在線的所有activity:" + activityName);
        }
    }

    private void changeFragment(Fragment fragment,String TAG){
        binding.includeTitleCloseBar.tvTile.setText(TAG.equals("LOGIN") ? "Login" : "REGISTER");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment, TAG);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }



    private void addFragment() {
        binding.mBtn1.setChecked(true);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flContainer, loginFragment, "LOGIN");
        fragmentTransaction.commit();
    }

    private void testMotinlayout() {
        // 監聽滾動事件
        binding.motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {}

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {}

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                if (currentId == R.id.start) {
                    // 滾動結束時，圖片完全可見
                } else if (currentId == R.id.end) {
                    // 滾動到底部時，圖片完全消失
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {}
        });

        // 監聽滾動事件，並觸發動畫
        ViewCompat.setOnApplyWindowInsetsListener(binding.motionLayout, (v, insets) -> {
            binding.motionLayout.setProgress(0); // 確保圖片一開始是完全可見的
            binding.motionLayout.transitionToEnd();
            return insets;
        });
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