package com.wen.flow.framework.manager.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppFrontBack {
    private  int activityStartCount = 0;
    private String TAG  = getClass().getSimpleName();

    public void register(Application application, AppFrontBackListener appFrontBackListener){
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                Log.v("hank "  ,"ActivityCallBack ->onActivityCreated()");
            }

            //當一個 Activity 變得可見且處於前台時，該方法會被調用。這表明 Activity 正在被用戶查看，但它可能不處於活動狀態。
            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                activityStartCount++;
                if (activityStartCount == 1) {
                    appFrontBackListener.onFront(activity);
                    Log.v("hank","onFront() activityName" +activity.getClass().getName());
                }
                Log.v("hank ", "ActivityCallBack ->onActivityStarted() activityName:" + activity.getLocalClassName() +",activityStartCount:" + activityStartCount);
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                Log.v("hank "  ,"ActivityCallBack ->onActivityResumed() activityName:" + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                Log.v("hank "  ,"ActivityCallBack ->onActivityPaused() activityName:" + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                activityStartCount--;
                if(activityStartCount == 0){
                    appFrontBackListener.onBack(activity);
                    Log.v("hank","onBack() activityName" +activity.getClass().getName());
                }
                Log.v("hank "  ,"ActivityCallBack ->onActivityStopped() activityName:" +  activity.getLocalClassName() +",activityStartCount:" + activityStartCount);
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                Log.v("hank "  ,"ActivityCallBack ->onActivitySaveInstanceState() activityName:" + activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                Log.v("hank "  ,"ActivityCallBack ->onActivityDestroyed() activityName:" + activity.getLocalClassName());
            }
        });
    }

   public interface AppFrontBackListener{
        void onFront(Activity activity);

        void onBack(Activity activity);
    }
}
