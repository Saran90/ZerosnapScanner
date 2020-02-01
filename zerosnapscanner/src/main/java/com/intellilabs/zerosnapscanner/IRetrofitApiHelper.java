package com.intellilabs.zerosnapscanner;

import retrofit2.Response;

public interface IRetrofitApiHelper<T> {
    void onSuccess(Response<T> response);
    void onError(String error);
}