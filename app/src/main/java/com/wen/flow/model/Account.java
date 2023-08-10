package com.wen.flow.model;

import java.io.Serializable;

public class Account implements Serializable {

    /**
     * result : false
     * data : {"errMsg":"USER_NAME_TYPE_ERROR","errCode":1010001}
     */

    private boolean result;
    private DataBean data;

    public boolean isResult() {
        return result;
    }

    public DataBean getData() {
        return data;
    }

    public static class DataBean implements Serializable {
        /**
         * errMsg : USER_NAME_TYPE_ERROR
         * errCode : 1010001
         */

        private String errMsg;
        private int errCode;

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public int getErrCode() {
            return errCode;
        }

        public void setErrCode(int errCode) {
            this.errCode = errCode;
        }
    }
}
