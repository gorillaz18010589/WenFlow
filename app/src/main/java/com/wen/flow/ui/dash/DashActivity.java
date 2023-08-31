package com.wen.flow.ui.dash;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wen.flow.MainActivity;
import com.wen.flow.R;
import com.wen.flow.databinding.ActivityDashBinding;
import com.wen.flow.support.base.BaseBindingActivity;
import com.wen.flow.support.custom.badge.Badge;
import com.wen.flow.ui.dash.home.HomeFragment;
import com.wen.flow.ui.dash.user.UserFragment;
import com.wen.flow.ui.login_register.LoginRegisterActivity;

import java.util.ArrayList;

import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED;

public class DashActivity extends BaseBindingActivity<ActivityDashBinding> {
    private static final SparseIntArray NAV_TAB_DIRECTIONS = new SparseIntArray();
    private NavController navController;

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
    }

    @Override
    protected void initListeners() {
        binding.customBottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
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
                if (isGust) startLoginRegisterActivity(currentId);
                navController.popBackStack();
                navController.navigate(currentId);
                return true;
            }
            return false;
        }
    };

    private void startLoginRegisterActivity(int actionId) {
        startActivity(new Intent(DashActivity.this,LoginRegisterActivity.class));
//        overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
    }

}
