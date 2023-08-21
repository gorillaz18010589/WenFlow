package com.wen.flow.ui.login_register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.wen.flow.R;
import com.wen.flow.databinding.ActivityLoginRegisterBinding;
import com.wen.flow.enums.TitleCloseBarEnum;
import com.wen.flow.framework.log.KLog;
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
    private NavController navController;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_register;
    }


    @Override
    protected void init() {
        initHideShowNavFragmentController();
        binding.includeTitleCloseBar.setTitleCloseBarEnum(TitleCloseBarEnum.Login);
        binding.includeTitleCloseBar.setIconEnum(IconEnum.ICON_CLOSE);
        binding.includeTitleCloseBar.shapeImageBtnCancel.setOnClickListener(v -> {
            getAppDeviceInformation();
        });
        Log.v("hank", "init()");
    }

    @Override
    protected void initListeners() {
        binding.toggleButtonGroup.addOnButtonCheckedListener(onButtonCheckedListener);
        navController.addOnDestinationChangedListener(onDestinationChangedListener);
    }

    MaterialButtonToggleGroup.OnButtonCheckedListener onButtonCheckedListener = new MaterialButtonToggleGroup.OnButtonCheckedListener() {
        @Override
        public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
            KLog.v(TAG + "onButtonChecked -> checkedId:" + checkedId + ",isChecked:" + isChecked);
            int desId = navController.getCurrentDestination().getId();

            if (checkedId == R.id.mBtn1 && desId == R.id.registerFragment) {
                showToast("mBtn1");
                navController.popBackStack();
                navController.navigate(R.id.loginFragment);
                if (navController != null) {
                    KLog.v("mBtn1 -> desId:" + desId);
                }
            } else if (checkedId == R.id.mBtn2 && desId == R.id.loginFragment) {
                showToast("mBtn2");
                navController.popBackStack();
                navController.navigate(R.id.registerFragment);
                if (navController != null) {
//                    int desId = navController.getCurrentDestination().getId();
                    KLog.v("mBtn2 -> desId:" + desId);
                }
            }
        }
    };

    NavController.OnDestinationChangedListener onDestinationChangedListener = new NavController.OnDestinationChangedListener() {
        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
            int currentId = navController.getCurrentDestination().getId();
            if (currentId == R.id.loginFragment ||
                    currentId == R.id.registerFragment) {
                if (!isToggleButtonGroupShow()) showIconAndGroundBtnView(true);
            }
        }
    };

    @Override
    public void onBackPressed() {
        int currentId = navController.getCurrentDestination().getId();
        if (currentId == R.id.loginFragment ||
                currentId == R.id.registerFragment) return;
        super.onBackPressed();
    }

    //nav replace方式初始化
    private void initNavController() {
//        navController = Navigation.findNavController(this, R.id.nav_login_register_fragment);
        binding.mBtn1.setChecked(true);
    }


    private void initHideShowNavFragmentController() {
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.loginRegisterNavHostFragment);
        navController = host.getNavController();
        binding.mBtn1.setChecked(true);
    }

    private void getAppInformation() {
        Log.v(TAG, new AppManager().toString());
    }


    private void getAppDeviceInformation() {
//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
//                .subscribe(g ->{
//                    if(g){
//                        showToast("允許");
//                    }else {
//                        showToast("沒有允許");
//                    }
//                });
        DeviceInfoUtils.init(this, this, true);
        String phoneDevice = DeviceInfoUtils.getPhoneDevice();
        String phoneBrand = DeviceInfoUtils.getPhoneBrand();
        String phoneManufacturer = DeviceInfoUtils.getPhoneManufacturer();
        String phoneModel = DeviceInfoUtils.getPhoneModel();
        String androidId = DeviceInfoUtils.getAndroidId();
        String imei = DeviceInfoUtils.getImei();
        String wifiSSID = DeviceInfoUtils.getWifiSSID();
        String mac = DeviceInfoUtils.getMac();

        Log.v(TAG, String.format("" +
                        "\n phoneDevice:%s\n" +
                        " phoneBrand:%s\n" +
                        " phoneManufacturer:%s\n" +
                        " phoneModel:%s\n" +
                        " androidId:%s\n" +
                        " imei:%s\n" +
                        " wifiSSID:%s\n" +
                        " mac:%s\n"
                , phoneDevice, phoneBrand, phoneManufacturer, phoneModel, androidId, imei, wifiSSID, mac));
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
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            String activityName = activity.getLocalClassName();
            Log.v("hank", "目前在線的所有activity:" + activityName);
        }
    }

    public void startEmailFragment() {
        showIconAndGroundBtnView(false);
        binding.includeTitleCloseBar.tvTile.setText("信箱驗證");
        navController.navigate(R.id.emailCodeFragment);
    }

    public void showIconAndGroundBtnView(boolean isShow) {
        binding.imgLogo.setVisibility(isShow ? View.VISIBLE : View.GONE);
        binding.toggleButtonGroup.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public boolean isToggleButtonGroupShow() {
        return binding.toggleButtonGroup.getVisibility() == View.VISIBLE ? true : false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("hank", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("hank", "onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("hank", "onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("hank", "onRestart()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.v("hank", "onCreate()");
    }
}