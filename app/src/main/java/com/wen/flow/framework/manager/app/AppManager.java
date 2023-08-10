package com.wen.flow.framework.manager.app;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.wen.flow.support.util.DeviceInfoUtils;


public class AppManager {

    private static final String TAG = AppManager.class.getSimpleName();

    private static Application mContext;

    private static int mScreenWidthPx = 0;

    private static int mScreenHeightPx = 0;

    private static int mScreenWidthDp = 0;

    private static int mScreenHeightDp = 0;

    /**
     * 密度 dpi
     */
    private static int mDensityDpi = 0;

    /**
     * 密度比例
     */
    private static float mDensity = 0f;

    /**
     * 狀態欄高度
     */
    private static int mStatusBarHeight = 0;

    /**
     * 產品類型
     */
    private static String mProductType;

    private static boolean mIsBiggerScreen = false;

    public static void init(Application application) {
        mContext = application;
        WindowManager windowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenHeightPx = Math.max(metrics.heightPixels, metrics.widthPixels);
        mScreenWidthPx = Math.min(metrics.heightPixels, metrics.widthPixels);
        mIsBiggerScreen = mScreenHeightPx * 1.0 / mScreenWidthPx > 16.0 / 9;
        mDensityDpi = metrics.densityDpi;
        mDensity = metrics.density;
        mScreenHeightDp = (int) (mScreenHeightPx / mDensity);
        mScreenWidthDp = (int) (mScreenWidthPx / mDensity);

        int resourceId = application.getResources().getIdentifier("status_bar_height", "dimen", "android");
        mStatusBarHeight = application.getResources().getDimensionPixelSize(resourceId);
        mProductType = genProductType();
    }

    public static int getScreenWidthPx() {
        return mScreenWidthPx;
    }

    public static int getScreenHeightPx() {
        return mScreenHeightPx;
    }

    public static int getScreenContentHeightPx() {
        return mScreenHeightPx - getStatusBarHeight();
    }

    public static int getScreenWidthDp() {
        return mScreenWidthDp;
    }

    public static int getScreenHeightDp() {
        return mScreenHeightDp;
    }

    public static int getDensityDpi() {
        return mDensityDpi;
    }

    public static float getDensity() {
        return mDensity;
    }

    public static int getStatusBarHeight() {
        return mStatusBarHeight;
    }

    public static String getProductType() {
        return mProductType;
    }

    private static String genProductType() {
        String model = DeviceInfoUtils.getPhoneModel();
        return model.replaceAll("[:{} \\[\\]\"']*", "");
    }

    /**
     * 獲取魅族 SmartBar 高度
     *
     * @return
     */
    public static int getSmartBarHeight() {
        if (isMeizu() && hasSmartBar()) {
            boolean autoHideSmartBar = Settings.System.getInt(mContext.getContentResolver(), "mz_smartbar_auto_hide", 0) == 1;
            return autoHideSmartBar ? 0 : getNormalNavigationBarHeight();
        }
        return 0;
    }

    private static int getNormalNavigationBarHeight() {
        try {
            Resources res = mContext.getResources();
            int rid = res.getIdentifier("config_showNavigationBar", "bool", "android");
            if (rid > 0) {
                boolean flag = res.getBoolean(rid);
                if (flag) {
                    int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
                    if (resourceId > 0) {
                        return res.getDimensionPixelSize(resourceId);
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判斷是否為魅族手機
     *
     * @return
     */
    public static boolean isMeizu() {
        return Build.MANUFACTURER.equalsIgnoreCase("Meizu");
    }

    public static boolean hasSmartBar() {
        try { // 新型號可用反射調用 Build.hasSmartBar()
            Class<?> cls = Class.forName("android.os.Build");
            java.lang.reflect.Method method = cls.getMethod("hasSmartBar");
            return (boolean) method.invoke(null);
        } catch (Exception e) {
            Log.e(TAG, "hasSmartBar", e);
        }
        // 反射不到 Build.hasSmartBar()，則用 Build.DEVICE 判斷
        return Build.DEVICE.equals("mx2") || Build.DEVICE.equals("mx") || Build.DEVICE.equals("m9");
    }

    public static boolean isBiggerScreen() {
        return mIsBiggerScreen;
    }

    /**
     * 獲取手機品牌商
     */
    public static String getDeviceBuildBrand() {
        return Build.BRAND != null ? Build.BRAND : "";
    }

    /**
     * 獲取手機型號
     */
    public static String getDeviceBuildModel() {
        return DeviceInfoUtils.getPhoneModel();
    }

    /**
     * 獲取手機系統版本號
     */
    public static String getDeviceBuildRelease() {
        return Build.VERSION.RELEASE != null ? Build.VERSION.RELEASE : "";
    }

    public static int dip2px(float dipValue) {
        return (int) (dipValue * mDensity + 0.5f);
    }

    /**
     * 獲取版本名稱
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            String packageName = context.getPackageName();
            pm.getPackageInfo(packageName, 0);
            android.content.pm.PackageInfo pi = pm.getPackageInfo(packageName, 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.isEmpty()) {
                return "";
            }
        } catch (Exception e) {
            Log.v(TAG,"VersionInfo", e);
        }
        return versionName;
    }

    /**
     * 獲取版本號
     */
    public static long getAppVersionCode(Context context) {
        long appVersionCode = 0;
        try {
            String packageName = context.getPackageName();
            android.content.pm.PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(packageName, 0);
            appVersionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.v(TAG,"getAppVersionCode-" + e.getMessage());
        }
        return appVersionCode;
    }

    @Override
    public String toString() {
        return "PhoneInfoManager{" +
                "mScreenWidthPx=" + mScreenWidthPx +
                ", mScreenHeightPx=" + mScreenHeightPx +
                ", mScreenWidthDp=" + mScreenWidthDp +
                ", mScreenHeightDp=" + mScreenHeightDp +
                ", mDensityDpi=" + mDensityDpi +
                ", mDensity=" + mDensity +
                ", mStatusBarHeight=" + mStatusBarHeight +
                '}';
    }
}
