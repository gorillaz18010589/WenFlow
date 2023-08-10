package com.wen.flow.framework.manager.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityManager {

    // 存儲 Activity 的任務列表
    private static List<Activity> tasks = new ArrayList<>();

    // 將 Activity 加入任務列表
    public static void push(Activity activity) {
        tasks.add(activity);
        Log.v("hank","ActivityManager -> push()" + activity.getLocalClassName());
    }

    // 從任務列表中移除指定的 Activity
    public static void pop(Activity activity) {
        tasks.remove(activity);
        Log.v("hank","ActivityManager -> pop()" + activity.getLocalClassName());
    }

    // 獲取任務列表中的最頂層 Activity，即當前正在前台運行的 Activity
    public static Activity top() {
        if (!tasks.isEmpty()) {
            return tasks.get(tasks.size() - 1);
        }
        return null;
    }

    // 結束所有的 Activity，並在結束後執行指定的回調函數（可選）
    public static void finishAllActivity(Runnable callback) {
        Iterator<Activity> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            iterator.remove();
            activity.finish();
        }
        if (callback != null) {
            callback.run();
        }
    }

    // 結束除指定類型的 Activity 之外的所有 Activity
    public static void finishOtherActivity(Class<? extends Activity> clazz) {
        Iterator<Activity> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass() != clazz) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    // 結束指定類型的 Activity
    public static void finishActivity(Class<? extends Activity> clazz) {
        Iterator<Activity> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass() == clazz) {
                iterator.remove();
                activity.finish();
                break;
            }
        }
    }

    // 檢查指定類型的 Activity 是否存在於任務列表中
    public static boolean isActivityExistsInStack(Class<? extends Activity> clazz) {
        if (clazz != null) {
            for (Activity task : tasks) {
                if (task.getClass() == clazz) {
                    return true;
                }
            }
        }
        return false;
    }

    // 檢查傳入的 Context 對應的 Activity 是否已被銷毀
    public static boolean isActivityDestroy(Context context) {
        Activity activity = findActivity(context);
        if (activity != null) {
            if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
                return activity.isDestroyed() || activity.isFinishing();
            } else {
                return activity.isFinishing();
            }
        }
        return true;
    }

    // 在 ContextWrapper 的包裝中查找 Activity
    private static Activity findActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static List<Activity> getTasksActivity() {
        return tasks;
    }
}