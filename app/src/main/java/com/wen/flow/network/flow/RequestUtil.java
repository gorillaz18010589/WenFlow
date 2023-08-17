package com.wen.flow.network.flow;


import com.google.gson.Gson;
import com.wen.flow.framework.log.KLog;
import com.wen.flow.network.callback.SuccessResponseCallback;
import com.wen.flow.network.error.ApiException;
import com.wen.flow.network.error.ERROR;
import com.wen.flow.network.error.ExceptionHandler;
import com.wen.flow.network.response.BaseResponse;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.wen.flow.MyApplication.TAG;

public class RequestUtil {

    // 定義 FlowResponseCallback，處理 Flowable 的回調
    public interface FlowResponseCallback<T> {
        Flowable<BaseResponse<T>> onResponse();
    }

    // 定義 IApiErrorCallback，處理 API 錯誤的回調
    public interface IApiErrorCallback {
        void onLoginFail(int code, String errorMsg);

        void onError(int code, String errorMsg);
    }

    // 處理 Flowable 的 API 請求
    public static <T> void requestFlow(
            IApiErrorCallback errorCall,
            FlowResponseCallback<T> responseBlock,
            Consumer<Boolean> showLoading,
            SuccessResponseCallback<T> successBlock
    ) {
        Flowable<BaseResponse<T>> flowable = requestFlowResponse(errorCall, responseBlock, showLoading);

        flowable.subscribe(response -> {
            if (!response.isResult()) {
//                throw new ApiException(response.getErrCode(), response.getErrMsg());
                KLog.json(TAG + "requestFlow -> !isResult" + new Gson().toJson(response));
            }
            successBlock.onResponse(response);
        }, throwable -> {
            KLog.v(throwable);
            handleApiError(throwable, errorCall);
        });
    }

    // 處理 Flowable 的 API 請求並處理加載框等操作
    private static <T> Flowable<BaseResponse<T>> requestFlowResponse(
            IApiErrorCallback errorCall,
            FlowResponseCallback<T> responseBlock,
            Consumer<Boolean> showLoading
    ) {
        return responseBlock.onResponse()
                .timeout(10 * 1000, java.util.concurrent.TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> showLoading.accept(true))
                .doFinally((Action) () -> showLoading.accept(false))
                .doOnError(throwable -> handleApiError(throwable, errorCall));
    }

    // 處理 API 錯誤
    private static void handleApiError(Throwable throwable, IApiErrorCallback errorCall) {
        ApiException exception = ExceptionHandler.handleException(throwable);
        if (ERROR.UNLOGIN.getCode() == exception.getErrCode()) {
            errorCall.onLoginFail(exception.getErrCode(), exception.getErrMsg());
        } else {
            errorCall.onError(exception.getErrCode(), exception.getErrMsg());
        }
    }
}
