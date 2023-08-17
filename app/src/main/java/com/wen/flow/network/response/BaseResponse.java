package com.wen.flow.network.response;

import java.io.Serializable;


public class BaseResponse<T> implements Serializable {


    /**
     * result : false
     * data : {"errMsg":"USER_NAME_TYPE_ERROR","errCode":1010001}
     */

    private boolean result;
    private DataBean data;
    private T bean;

    public BaseResponse(boolean result, DataBean data, T bean) {
        this.result = result;
        this.data = data;
        this.bean = bean;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * errMsg : USER_NAME_TYPE_ERROR
         * errCode : 1010001
         */

        private String errMsg;
        private int errCode;
    }


}


//public class BaseResponse<T> implements Serializable {
//    private T Data;
//    private boolean result;
//    private String errMsg;
//    private int errCode;
//
//    public BaseResponse(T data, boolean result, String errMsg, int errCode) {
//        Data = data;
//        this.result = result;
//        this.errMsg = errMsg;
//        this.errCode = errCode;
//    }
//
//    public BaseResponse() {
//    }
//
//    public T getData() {
//        return Data;
//    }
//
//    public void setData(T data) {
//        Data = data;
//    }
//
//    public boolean isResult() {
//        return result;
//    }
//
//    public void setResult(boolean result) {
//        this.result = result;
//    }
//
//    public String getErrMsg() {
//        return errMsg;
//    }
//
//    public void setErrMsg(String errMsg) {
//        this.errMsg = errMsg;
//    }
//
//    public int getErrCode() {
//        return errCode;
//    }
//
//    public void setErrCode(int errCode) {
//        this.errCode = errCode;
//    }
//}