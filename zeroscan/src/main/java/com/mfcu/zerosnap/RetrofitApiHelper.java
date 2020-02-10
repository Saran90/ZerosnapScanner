package com.mfcu.zerosnap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitApiHelper<T> {

    public RetrofitApiHelper(){
    }

    public void performApiCall(Call<T> call,final IRetrofitApiHelper<T> iRetrofitApiHelper){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.code()== NetworkUtils.RESPONSE_OK){
                    iRetrofitApiHelper.onSuccess(response);
                }else {
                    iRetrofitApiHelper.onError("Failed!");
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                iRetrofitApiHelper.onError(t.getMessage());
            }
        });
    }
}