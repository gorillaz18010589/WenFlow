package com.wen.flow.network.error;

import java.io.IOException;

//定義一個結果異常的基類
/**
 * 結果異常類
 * 伺服器非200狀態，對應的異常
 */
public class ApiException extends Exception {
    private int errCode;
    private String errMsg;

    public ApiException(ERROR error, Throwable e) {
        super(e);
        errCode = error.getCode();
        errMsg = error.getErrMsg();
    }

    public ApiException(int code, String msg, Throwable e) {
        super(e);
        this.errCode = code;
        this.errMsg = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}

/**
 * 無網路連接異常
 */
 class NoNetWorkException extends IOException {
    private int errCode;
    private String errMsg;

    public NoNetWorkException(ERROR error, Throwable e) {
        super(e);
        errCode = error.getCode();
        errMsg = error.getErrMsg();
    }
}
