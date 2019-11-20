package com.mfcu.zerosnap;

import android.app.Application;

import com.intellilabs.zerosnapscanner.ZerosnapScanner;

/**
 * Created by Saran M S on 11/20/2019.
 */
public class ZerosnapApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /*
        * Register with licence key
        * */
        ZerosnapScanner.register("D0286312FB9588FDCA3CC9689143FA5E971139A5761CD51A1DF60AD529090A774A6A17ED6CD539D4CED45401D1231DE3");
    }
}
