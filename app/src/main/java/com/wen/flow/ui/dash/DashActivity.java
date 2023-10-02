package com.wen.flow.ui.dash;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.wen.flow.MainActivity;
import com.wen.flow.R;
import com.wen.flow.databinding.ActivityDashBinding;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.network.request.ApiRequest;
import com.wen.flow.network.request.SendEmailCodeRequest;
import com.wen.flow.network.response.LoginModel;
import com.wen.flow.support.base.BaseBindingActivity;
import com.wen.flow.support.custom.badge.Badge;
import com.wen.flow.support.util.DisplayUtil;
import com.wen.flow.ui.dash.home.HomeFragment;
import com.wen.flow.ui.dash.user.UserFragment;
import com.wen.flow.ui.login_register.LoginRegisterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED;

public class DashActivity extends BaseBindingActivity<ActivityDashBinding> {
    private static final SparseIntArray NAV_TAB_DIRECTIONS = new SparseIntArray();
    public NavController navController;

    static {
        NAV_TAB_DIRECTIONS.put(R.id.navigation_home, R.id.homeFragment);
        NAV_TAB_DIRECTIONS.put(R.id.navigation_shop, R.id.shopFragment);
        NAV_TAB_DIRECTIONS.put(R.id.navigation_shopping_cart, R.id.shoppingCartFragment);
        NAV_TAB_DIRECTIONS.put(R.id.navigation_account, R.id.userFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dash;
    }

    @Override
    protected void init() {
        navController = Navigation.findNavController(this, R.id.DashNavHostFragment);
        NavigationUI.setupWithNavController(binding.customBottomNav, navController);
        setBottomNavigationView();
        getBottomNaivgaionViewInformation();
    }

    private void getBottomNaivgaionViewInformation() {
        binding.customBottomNav.post(new Runnable() {
            @Override
            public void run() {
                int height = DisplayUtil.px2dp(DashActivity.this,binding.customBottomNav.getHeight());;
                int measuredHeight = DisplayUtil.px2dp(DashActivity.this,binding.customBottomNav.getMeasuredHeight());
                int minMumHeight = binding.customBottomNav.getMinimumHeight();
                KLog.v("customBottomNav height:" + height +", measuredHeight:"+ measuredHeight +", minMumHeight:" + minMumHeight);
            }
        });
    }

    @Override
    protected void initListeners() {
        binding.customBottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navController.addOnDestinationChangedListener(onDestinationChangedListener);
    }

    NavController.OnDestinationChangedListener onDestinationChangedListener = new NavController.OnDestinationChangedListener() {
        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
            int fragmentId = navController.getCurrentDestination().getId();
            if (fragmentId == R.id.homeFragment ||
                    fragmentId == R.id.shopFragment ||
                    fragmentId == R.id.shoppingCartFragment ||
                    fragmentId == R.id.userFragment
            ) {
                showBottomNavigationView(true);
                return;
            } else {
                showBottomNavigationView(false);
                return;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setBottomNavigationView() {
        binding.customBottomNav.setItemTextAppearanceInactive(R.style.bottomNavTitleTextDefault);
        binding.customBottomNav.setItemTextAppearanceActive(R.style.bottomNavTitleTextSelected);
        binding.customBottomNav.setLabelVisibilityMode(LABEL_VISIBILITY_LABELED);
        binding.customBottomNav.setItemIcon(0, R.drawable.icon_home_default, R.drawable.icon_home_checked);
        binding.customBottomNav.setItemIcon(1, R.drawable.icon_shop_default, R.drawable.icon_shop_checked);
        binding.customBottomNav.setItemIcon(2, R.drawable.icon_shopping_car_default, R.drawable.icon_shopping_car_checked);
        binding.customBottomNav.setItemIcon(3, R.drawable.icon_account_default, R.drawable.icon_account_checked);
    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            int currentId = NAV_TAB_DIRECTIONS.get(id);
            if (navController.getCurrentDestination().getId() != currentId || currentId== R.id.userFragment) {
                boolean isGust = currentId == R.id.userFragment;
                if (isGust) startLoginRegisterActivity(LOGIN_REGISTER_ACTIVITY_START_REQUEST_CODE);
                navController.popBackStack();
                navController.navigate(currentId);
                return true;
            }
            return false;
        }
    };

    private void startLoginRegisterActivity() {
        startActivity(new Intent(DashActivity.this,LoginRegisterActivity.class));
//        overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
    }

    public static final int LOGIN_REGISTER_ACTIVITY_START_REQUEST_CODE = 111;
    private void startLoginRegisterActivity(int requestCode) {
        startActivityForResult(new Intent(DashActivity.this,LoginRegisterActivity.class), requestCode);
//        overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REGISTER_ACTIVITY_START_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                LoginModel loginModel = data.getParcelableExtra(new LoginModel().getTagName());
                updateUser(loginModel);
            }
        }

    }

    private void updateUser(LoginModel loginModel) {
        showToast("登入成功:" + loginModel.getUserName());
    }

    private void test(){
        HashMap<String,ApiRequest> apiRequestDatas2 = new HashMap<>();
        HashSet<ApiRequest> set1 = new HashSet<>();
        HashSet<ApiRequest> set2 = new HashSet<>();
        HashSet<ApiRequest> set3 = new HashSet<>();
        set1.add(new SendEmailCodeRequest("123","111","333"));
        set2.add(new SendEmailCodeRequest("123","111","333"));
        set3.add(new SendEmailCodeRequest("443","323","111"));

        apiRequestDatas2.put("1",new SendEmailCodeRequest("123","111","333"));
        apiRequestDatas2.put("2",new SendEmailCodeRequest("123","111","333"));
        apiRequestDatas2.put("3",new SendEmailCodeRequest("123","111","333"));

        KLog.json(new Gson().toJson(apiRequestDatas2));
    }

    public void showBottomNavigationView(boolean show) {
        binding.customBottomNav.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
