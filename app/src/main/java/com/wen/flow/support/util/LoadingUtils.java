package com.wen.flow.support.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.wen.flow.support.loading.CenterLoadingView;

public class LoadingUtils {
    private Context mContext;
    private CenterLoadingView loadingView;

    public LoadingUtils(Context context) {
        this.mContext = context;
    }

    public void show(String txt) {
        if (loadingView == null) {
            loadingView = new CenterLoadingView(mContext);
        }
        if(loadingView.isShowing()){
            loadingView.dismiss();
        }
        if(!TextUtils.isEmpty(txt)){
            loadingView.setTitle(txt);
        }
        if(mContext instanceof Activity && ((Activity)mContext).isFinishing()){
            return;
        }
        loadingView.show();
    }

    public void dismissLoading() {
        if (mContext instanceof Activity && ((Activity) mContext).isFinishing()) {
            return;
        }

        if (loadingView != null && loadingView.isShowing()) {
            loadingView.dismiss();
        }
    }

    public CenterLoadingView getLoadingView() {
        if(loadingView == null) loadingView = new CenterLoadingView(mContext);
        return loadingView;
    }
}
