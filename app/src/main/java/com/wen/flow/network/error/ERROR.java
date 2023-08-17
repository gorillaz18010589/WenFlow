package com.wen.flow.network.error;

public enum ERROR {
    BAD_REQUEST (-99, "未知錯誤"),
    UNAUTHORIZED(401, "當前請求需要使用者驗證"),
    FORBIDDEN(403, "資源不可用"),
    NOT_FOUND(404, "無法找到指定位置的資源"),
    REQUEST_TIMEOUT(408, "請求超時"),
    INTERNAL_SERVER_ERROR(500, "伺服器錯誤"),
    BAD_GATEWAY(502, "不正當的應答"),
    SERVICE_UNAVAILABLE(503, "伺服器未能應答"),
    GATEWAY_TIMEOUT(504, "伺服器未能應答"),
    UNKNOWN(1000, "未知錯誤"),
    PARSE_ERROR(1001, "解析錯誤"),
    NETWORD_ERROR(1002, "網路異常，請嘗試重新整理"),
    HTTP_ERROR(1003, "404 找不到資源"),
    SSL_ERROR(1004, "證書錯誤"),
    TIMEOUT_ERROR(1006, "連線超時"),
    UNLOGIN(-1001, "未登入"),
    UNKNOW_HOST(1007, "未知主機");

    private final int code;
    private final String errMsg;

    ERROR(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }


    public static ERROR getERROR(int code) {
        for (ERROR error : values()) {
            if (error.getCode() == code)
                return error;
        }
        return BAD_REQUEST;
    }
}

