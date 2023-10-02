package com.wen.flow.network.request;

import com.wen.flow.network.error.ApiException;

import java.io.Serializable;

public class ApiRequest implements Serializable {
    public String getTagName(){
        return getClass().getSimpleName();
    }
}
