package com.wen.flow.network.response;

import com.wen.flow.network.error.ApiException;

public abstract class ApiResult<T> {
    private ApiException apiException;

    public ApiException getApiException() {
        return apiException;
    }

    public void setApiException(ApiException apiException) {
        this.apiException = apiException;
    }

}
