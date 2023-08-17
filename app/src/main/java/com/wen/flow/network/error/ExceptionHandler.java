package com.wen.flow.network.error;

import android.net.ParseException;

import com.wen.flow.MyApplication;
import com.wen.flow.support.toast.TipsToast;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import retrofit2.HttpException;
import org.json.JSONException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ExceptionHandler {

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof ApiException) {
            ex = new ApiException(((ApiException) e).getErrCode(), ((ApiException) e).getErrMsg(), e);
            if (ex.getErrCode() == ERROR.UNLOGIN.getCode()) {
                // 登录失效的处理
            }
        } else if (e instanceof NoNetWorkException) {
            TipsToast.getInstance(MyApplication.getInstance()).showTips("ExceptionHandler ->网络异常，请尝试刷新");
            ex = new ApiException(ERROR.NETWORD_ERROR, e);
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.message();
            ex = new ApiException(code, msg, e);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                || e instanceof MalformedJsonException
        ) {
            ex = new ApiException(ERROR.PARSE_ERROR, e);
        } else if (e instanceof ConnectException) {
            ex = new ApiException(ERROR.NETWORD_ERROR, e);
        } else if (e instanceof javax.net.ssl.SSLException) {
            ex = new ApiException(ERROR.SSL_ERROR, e);
        } else if (e instanceof SocketException) {
            ex = new ApiException(ERROR.TIMEOUT_ERROR, e);
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(ERROR.TIMEOUT_ERROR, e);
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(ERROR.UNKNOW_HOST, e);
        } else {
            if (e.getMessage() != null && !e.getMessage().isEmpty()) {
                ex = new ApiException(1000, e.getMessage(), e);
            } else {
                ex = new ApiException(ERROR.UNKNOWN, e);
            }
        }
        return ex;
    }

}
