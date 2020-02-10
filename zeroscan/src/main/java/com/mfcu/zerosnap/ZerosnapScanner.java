package com.mfcu.zerosnap;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Saran M S on 11/20/2019.
 */
public class ZerosnapScanner {

    private static Context mContext;
    private static ZerosnapScannerType mZerosnapScannerType;
    private static ZerosnapScannerCallback mZerosnapScannerCallback;
    private static String licenceKey,clientId,userId,applicationId;

    /*
    * Register Zerosnap Scanner library with licence key
    * */
    public static void register(String appId){
        applicationId = appId;
    }

    /**
     * Initialize Zerosnap Scanner with document type and callback
     * @param context
     * @param id - userid
     * @param zerosnapScannerCallback
     */
    public static void init(Context context,String id,
                            ZerosnapScannerCallback zerosnapScannerCallback){
        mContext = context;
        mZerosnapScannerCallback = zerosnapScannerCallback;
        userId = id;
        applicationId = context.getPackageName();
    }

    /**
     * Scan document by redirecting to Scanning page.
     * @param zerosnapScannerType
     */
    public static void scan(ZerosnapScannerType zerosnapScannerType){
        mZerosnapScannerType = zerosnapScannerType;
        Intent intent = InitActivity.newIntent(mContext,mZerosnapScannerCallback
                ,mZerosnapScannerType,userId,applicationId);
        mContext.startActivity(intent);
    }
}
