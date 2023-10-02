package com.wen.flow.network.response;

import com.wen.flow.network.error.ApiException;

import java.io.Serializable;

public abstract class ApiResult<T> implements Serializable {
    private ApiException apiException;

    public ApiException getApiException() {
        return apiException;
    }

    public void setApiException(ApiException apiException) {
        this.apiException = apiException;
    }

    public String getTagName (){
        return getClass().getSimpleName();
    }
}
