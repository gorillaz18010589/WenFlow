package com.wen.flow.support.custom.bottom_navigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wen.flow.support.custom.badge.Badge;
import com.wen.flow.support.custom.badge.QBadgeView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CustomBottomNavigationView extends BottomNavigationView {
    private BottomNavigationMenuView mMenuView;
    private BottomNavigationItemView[] mBottomNavigationItems;
    private ArrayList<Badge> mBadges;

    public CustomBottomNavigationView(@NonNull Context context) {
        super(context);
    }

    public CustomBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        initMenuView();
        initBottomNavigationItemViews();
        initBadges();
    }

    private void initMenuView() {
        if (null == mMenuView) {
            try {
                mMenuView = getField(BottomNavigationView.class, this, "menuView");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initBottomNavigationItemViews() {
        if (mBottomNavigationItems == null) {
            try {
                mBottomNavigationItems = getField(getMenuView().getClass(), getMenuView(), "buttons");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initBadges() {
        mBadges = new ArrayList<>();
        for (int i = 0; i < getMaxItemCount(); i++) {
            Badge badge = new QBadgeView(getContext())
                    .setGravityOffset(12, 2, true)
                    .bindTarget(getBottomNavigationItem(i));
            mBadges.add(badge);
        }
    }

    /*
     * 設定指定itemIcon
     * @param itemPosition        item位置
     * ＠param defaultIconResId    預設icon
     * ＠param selectedIconResId   selectedIcon
     * */
    public void setItemIcon(int itemPosition, int defaultIconResId, int selectedIconResId) {
        if (itemPosition < 0 || itemPosition >= getMenu().size()) return;
        MenuItem item = getMenu().getItem(itemPosition);
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable defaultIcon = ContextCompat.getDrawable(getContext(), defaultIconResId);
        stateListDrawable.addState(new int[]{-android.R.attr.state_selected}, defaultIcon);
        Drawable selectedIcon = ContextCompat.getDrawable(getContext(), selectedIconResId);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedIcon);
        item.setIcon(stateListDrawable);
    }

    /*
     * 清除item紅點通知
     * @param itemPosition item位置
     * */
    public void cleanItemBadge(int itemPosition) {
        mBadges.get(itemPosition).hide(true);
    }

    /*
     * 設定指定item紅點通知及數字
     * @param itemPosition item位置
     * @param badgeNumber  通知的數字
     * */
    public void setItemBadgeNumber(int itemPosition, int badgeNumber) {
        mBadges.get(itemPosition).setBadgeNumber(badgeNumber);
    }

    public Badge getItemBadge(int itemPosition) {
        return mBadges.get(itemPosition);
    }

    public ArrayList<Badge> getItemBadges() {
        return mBadges;
    }

    /*
     * 反射取得class內指定的屬性
     * @param targetClass  由
     * @param itemPosition item位置
     * @param itemPosition item位置
     * */
    private <T> T getField(Class<?> targetClass, Object targetObject, String fieldName)
            throws Exception {
        Field field = targetClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(targetObject);
    }

    public BottomNavigationMenuView getMenuView() {
        return mMenuView;
    }

    public BottomNavigationItemView[] getBottomNavigationItems() {
        return mBottomNavigationItems;
    }

    public BottomNavigationItemView getBottomNavigationItem(int position) {
        return mBottomNavigationItems[position];
    }
}
