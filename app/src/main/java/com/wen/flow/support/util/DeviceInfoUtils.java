package com.wen.flow.support.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.tbruyelle.rxpermissions3.RxPermissions;
import com.wen.flow.ui.login_register.LoginRegisterActivity;

import java.io.FileInputStream;
import java.net.NetworkInterface;
import java.util.Enumeration;

import static com.wen.flow.MyApplication.TAG;

/**
 * 設備信息的單例工具類
 * 手機imei需要權限<uses-permission android:name="android.permission.READ_PHONE_STATE"/> 危險權限
 * Wifi需要權限<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/> 普通權限
 */
public class DeviceInfoUtils {
    private static Context appContext;

    private static AppCompatActivity mActivity;

    // 設備imei號
    private static String imei = "";

    // 設備imsi號
    private static String imsi = "";

    // androidId
    private static String androidId = "";

    // 設備mac地址
    private static String mac = "";

    // WiFi mac地址
    private static String wifiMacAddress = "";

    // WiFi ssid號 ,unknown ssid : 沒連wifi
    private static String wifiSSID = "";

    //手機型號名稱
    private static String phoneModel = "";

    //手機品牌
    private static String phoneBrand = "";

    //手機製造商
    private static String phoneManufacturer = "";

    //手機裝置名稱
    private static String phoneDevice = "";

    /*
    *  @param content
    *  @param activity
    *  @param requestPermission 是否請求READ_PHONE_STATE跟ACCESS_WIFI_STATE全限 true:才可取得手機imei和wifi等權限 false：只取得基本裝置權限
    * */
    public static void init(Context context, AppCompatActivity activity, boolean requestPermission) {
        appContext = context.getApplicationContext();
        mActivity = activity;
        initDeviceInfo(requestPermission);
    }
    public static void init(Context context, AppCompatActivity activity) {
        init(context, activity,true);
    }

    /**
     * 刷新設備信息：
     */
    private static void initDeviceInfo(boolean requestPermission) {
        initImei(requestPermission);
        initMac(requestPermission);
        initAndroidId();
        phoneModel = Build.MODEL;
        phoneBrand = Build.BRAND;
        phoneManufacturer = Build.MANUFACTURER;
        phoneDevice = Build.DEVICE;
    }


    /**
     * 初始化AndroidId
     */
    @SuppressLint("HardwareIds")
    private static void initAndroidId() {
        if (!androidId.isEmpty()) {
            return;
        }
        Log.v(TAG, "init device androidId");
        String tmpAndroidId = Settings.Secure.getString(
                appContext.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        if (tmpAndroidId != null && !tmpAndroidId.isEmpty()) {
            androidId = tmpAndroidId.toLowerCase();
        }
    }

    /**
     * 初始化Mac地址
     */
    private static void initMac(boolean requestPermission) {
        if (!mac.isEmpty() || !wifiMacAddress.isEmpty() || !wifiSSID.isEmpty()) {
            return;
        }
        if (requestPermission) {
            RxPermissions rxPermissions = new RxPermissions(mActivity);
            rxPermissions.request(Manifest.permission.ACCESS_WIFI_STATE)
                    .subscribe(g -> {
                        if (g) {
                            try {
                                Log.v(TAG, "init device mac, wifiMac and ssid");
                                WifiManager wm = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
                                initMacAddress(wm);
                                if (wm.getConnectionInfo() != null && wm.getConnectionInfo().getBSSID() != null) {
                                    wifiMacAddress = wm.getConnectionInfo().getBSSID();
                                }
                                if (wm.getConnectionInfo() != null && wm.getConnectionInfo().getSSID() != null) {
                                    wifiSSID = wm.getConnectionInfo().getSSID();
                                }
                            } catch (Exception e) {
                                Log.v(TAG, "init mac failure", e);
                            }
                        } else {

                        }
                    });
        }
    }

    @SuppressLint("HardwareIds")
    private static void initMacAddress(WifiManager vm) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // 6.0以前可以從WifiInfo中獲取mac地址
                if (vm.getConnectionInfo() != null && vm.getConnectionInfo().getMacAddress() != null) {
                    mac = vm.getConnectionInfo().getMacAddress().toLowerCase();
                    Log.v(TAG, "initMacAddress() 6.0以前取得mac地址:" + mac);
                }
            } else {
                // 6.0~7.0讀取設備文件獲取
                String[] arrStrings = {"/sys/class/net/wlan0/address", "/sys/devices/virtual/net/wlan0/address"};
                for (String str : arrStrings) {
                    mac = readFile(str);
                    if (!mac.isEmpty()) {
                        break;
                    }
                }
                if (!mac.isEmpty()) {
                    return;
                }
                // 7.0及以上通過以下方式獲取
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                if (interfaces != null) {
                    while (interfaces.hasMoreElements()) {
                        NetworkInterface netInfo = interfaces.nextElement();
                        if ("wlan0".equals(netInfo.getName())) {
                            byte[] addresses = netInfo.getHardwareAddress();
                            if (addresses != null && addresses.length > 0) {
                                mac = macByte2String(addresses);
                                Log.v(TAG, "initMacAddress() 7.0以上取得mac地址:" + mac);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.v(TAG, "read mac failure", e);
        }
    }


    /**
     * 初始化imei、imsi
     */
    @SuppressLint("MissingPermission")
    private static void initImei() {
        Log.v(TAG, "initImei()");
        if (!imei.isEmpty() || !imsi.isEmpty()) {
            return;
        }
        if (appContext.checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "initImei() 沒權限return()");
            return;
        }
        try {
            Log.v(TAG, "init device imei and imsi");
            TelephonyManager tm = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
            String tmpImei = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tmpImei = tm.getImei();
            } else {
                tmpImei = tm.getDeviceId();
            }
            if (tmpImei != null && !tmpImei.isEmpty()) {
                imei = tmpImei.toLowerCase();
            }
            String tmpImsi = tm.getSubscriberId();
            if (tmpImsi != null && !tmpImsi.isEmpty()) {
                imsi = tmpImsi.toLowerCase();
            }
        } catch (Exception e) {
            Log.v(TAG, "initImei exception, msg=" + e.getMessage());
        }
    }

    /**
     * 初始化imei、imsi
     */
    @SuppressLint("MissingPermission")
    private static void initImei(boolean requestPermission) {
        Log.v(TAG, "initImei()");
        if (!imei.isEmpty() || !imsi.isEmpty()) {
            return;
        }

        if (requestPermission) {
            RxPermissions rxPermissions = new RxPermissions(mActivity);
            rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(g -> {
                        if (g) {
                            try {
                                Log.v(TAG, "init device imei and imsi");
                                TelephonyManager tm = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
                                String tmpImei = "";
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    tmpImei = tm.getImei();
                                } else {
                                    tmpImei = tm.getDeviceId();
                                }
                                if (tmpImei != null && !tmpImei.isEmpty()) {
                                    imei = tmpImei.toLowerCase();
                                }
                                String tmpImsi = tm.getSubscriberId();
                                if (tmpImsi != null && !tmpImsi.isEmpty()) {
                                    imsi = tmpImsi.toLowerCase();
                                }
                            } catch (Exception e) {
                                Log.v(TAG, "initImei exception, msg=" + e.getMessage());
                            }
                            Log.v(TAG, "有權限" + imsi);
                        } else {
                            Log.v(TAG, "沒權限");
                        }
                    });
        }
    }

    /**
     * 將mac的byte陣列轉化為string
     */
    private static String macByte2String(byte[] bytes) {
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * 從指定的文件內讀取內容
     */
    private static String readFile(String filePath) {
        String res = "";
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(filePath);
            int length = fin.available();
            byte[] buffer = new byte[length];
            int count = fin.read(buffer);
            if (count > 0) {
                res = new String(buffer, "UTF-8");
            }
        } catch (Exception e) {
            Log.v(TAG, "read file exception", e);
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (Exception e) {
                Log.v(TAG, "close FileInputStream failure");
            }
        }
        return res;
    }

    public static String getImei() {
        return imei;
    }

    public static String getImsi() {
        return imsi;
    }

    public static String getAndroidId() {
        return androidId;
    }

    public static String getMac() {
        return mac;
    }

    public static String getWifiMacAddress() {
        return wifiMacAddress;
    }

    public static String getWifiSSID() {
        return wifiSSID;
    }

    public static String getPhoneModel() {
        return phoneModel;
    }

    public static String getPhoneBrand() {
        return phoneBrand;
    }

    public static String getPhoneManufacturer() {
        return phoneManufacturer;
    }

    public static String getPhoneDevice() {
        return phoneDevice;
    }


    private static void requestPermission(String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions.request(permissions)
                .subscribe(g -> {
                    if (g) {
                        Log.v(TAG, "requestPermission -> 有權限");
                    } else {
                        Log.v(TAG, "requestPermission -> 沒權限");
                        return;
                    }
                });
    }


}