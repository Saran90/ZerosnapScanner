package com.intellilabs.zerosnapscanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.intellilabs.zerosnapscanner.applicationdetails.GetApplicationDetailsResponse;
import com.intellilabs.zerosnapscanner.checkSubscription.CheckSubscriptionResponse;
import com.scansolutions.mrzscannerlib.MRZResultModel;
import com.scansolutions.mrzscannerlib.MRZScanner;
import com.scansolutions.mrzscannerlib.MRZScannerListener;
import com.scansolutions.mrzscannerlib.ScannerType;

import retrofit2.Call;
import retrofit2.Response;

import static com.intellilabs.zerosnapscanner.SubscriptionActivity.LICENCE_KEY;
import static com.intellilabs.zerosnapscanner.SubscriptionActivity.ZEROSNAP_TOKEN;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_CLIENT_ID;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_DOCUMENT_TYPE;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_LICENCE_KEY;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_USER_ID;

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
     * @param zerosnapScannerCallback
     */
    public static void init(Context context,String id,
                            ZerosnapScannerCallback zerosnapScannerCallback){
        mContext = context;
        mZerosnapScannerCallback = zerosnapScannerCallback;
        userId = id;
    }

    /**
     * Scan document by redirecting to Scanning page.
     * @param zerosnapScannerType
     */
    public static void scan(ZerosnapScannerType zerosnapScannerType){
        mZerosnapScannerType = zerosnapScannerType;
        Intent intent = InitActivity.newIntent(mContext,mZerosnapScannerCallback
                ,mZerosnapScannerType,userId);
        mContext.startActivity(intent);
    }
}
