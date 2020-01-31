package com.intellilabs.zerosnapscanner;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.intellilabs.zerosnapscanner.NetworkUtils.BASE_URL;
import static com.intellilabs.zerosnapscanner.NetworkUtils.NETWORK_CONNECT_TIME_OUT;
import static com.intellilabs.zerosnapscanner.NetworkUtils.NETWORK_READ_TIME_OUT;
import static com.intellilabs.zerosnapscanner.NetworkUtils.NETWORK_WRITE_TIME_OUT;

public class RetrofitClientInstance {

    private static Retrofit retrofit;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private static OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(NETWORK_READ_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(NETWORK_CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_WRITE_TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}