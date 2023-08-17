package com.wen.flow.network.callback;

import com.wen.flow.MyApplication;
import com.wen.flow.support.toast.TipsToast;

public interface IApiErrorCallback {

   default void onError(int code, String errorMsg){
       TipsToast.getInstance(MyApplication.getInstance()).showTips(errorMsg);
   }


}
