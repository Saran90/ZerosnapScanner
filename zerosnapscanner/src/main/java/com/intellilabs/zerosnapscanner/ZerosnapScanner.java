package com.intellilabs.zerosnapscanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.scansolutions.mrzscannerlib.MRZResultModel;
import com.scansolutions.mrzscannerlib.MRZScanner;
import com.scansolutions.mrzscannerlib.MRZScannerListener;
import com.scansolutions.mrzscannerlib.ScannerType;

import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_DOCUMENT_TYPE;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_LICENCE_KEY;

/**
 * Created by Saran M S on 11/20/2019.
 */
public class ZerosnapScanner {

    private static Context mContext;
    private static ZerosnapScannerType mZerosnapScannerType;
    private static ZerosnapScannerCallback mZerosnapScannerCallback;
    private static String licenceKey;

    /*
    * Register Zerosnap Scanner library with licence key
    * */
    public static void register(String licence){
        licenceKey = licence;
    }

    /**
     * Initialize Zerosnap Scanner with document type and callback
     * @param context
     * @param zerosnapScannerType
     * @param zerosnapScannerCallback
     */
    public static void init(Context context,ZerosnapScannerType zerosnapScannerType,
                            ZerosnapScannerCallback zerosnapScannerCallback){
        mContext = context;
        mZerosnapScannerType = zerosnapScannerType;
        mZerosnapScannerCallback = zerosnapScannerCallback;
    }

    /**
     * Scan document by redirecting to Scanning page.
     */
    public static void scan(){
        Intent intent = ZerosnapScannerActivity.newIntent(mContext,mZerosnapScannerCallback);
        intent.putExtra(EXTRA_DOCUMENT_TYPE,mZerosnapScannerType.name());
        intent.putExtra(EXTRA_LICENCE_KEY,licenceKey);
        mContext.startActivity(intent);
    }
}
