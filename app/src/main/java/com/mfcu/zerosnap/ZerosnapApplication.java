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
//        ZerosnapScanner.register(BuildConfig.APPLICATION_ID);
    }
}
