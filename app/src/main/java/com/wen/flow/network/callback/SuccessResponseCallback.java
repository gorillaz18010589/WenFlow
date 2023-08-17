package com.wen.flow.network.callback;

import com.wen.flow.network.response.BaseResponse;

import io.reactivex.Flowable;

public interface SuccessResponseCallback<T> {

    void onResponse(BaseResponse<T> response);
}