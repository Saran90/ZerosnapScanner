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
     * @param zerosnapScannerType
     * @param zerosnapScannerCallback
     */
    public static void init(Context context,ZerosnapScannerType zerosnapScannerType,String id,
                            ZerosnapScannerCallback zerosnapScannerCallback){
        mContext = context;
        mZerosnapScannerType = zerosnapScannerType;
        mZerosnapScannerCallback = zerosnapScannerCallback;
        userId = id;
    }

    /**
     * Scan document by redirecting to Scanning page.
     */
    public static void scan(){
        if (clientId==null || licenceKey==null)
            getApplicationDetails();
        else
            navigateToScanPage();
    }

    private static void navigateToScanPage(){
        Intent intent = ZerosnapScannerActivity.newIntent(mContext,mZerosnapScannerCallback);
        intent.putExtra(EXTRA_DOCUMENT_TYPE,mZerosnapScannerType.name());
        intent.putExtra(EXTRA_LICENCE_KEY,licenceKey);
        intent.putExtra(EXTRA_USER_ID,userId);
        mContext.startActivity(intent);
    }

    private static void navigateToSubscriptionPage(){
        Intent intent1 = new Intent(mContext,SubscriptionActivity.class);
        intent1.putExtra(EXTRA_USER_ID,userId);
        intent1.putExtra(EXTRA_LICENCE_KEY,licenceKey);
        mContext.startActivity(intent1);
    }

    private static void checkSubscription() {
        SubscriptionService subscriptionService = RetrofitClientInstance
                .getRetrofitInstance().create(SubscriptionService.class);
        Call<CheckSubscriptionResponse> subscription = subscriptionService
                .checkSubscription(ZEROSNAP_TOKEN,
                        LICENCE_KEY,userId);
        RetrofitApiHelper<CheckSubscriptionResponse> retrofitApiHelper =
                new RetrofitApiHelper<CheckSubscriptionResponse>();
        retrofitApiHelper.performApiCall(subscription,
                new IRetrofitApiHelper<CheckSubscriptionResponse>() {
                    @Override
                    public void onSuccess(Response<CheckSubscriptionResponse> response) {
                        Log.d("TAG", "Response OK ");
                        if (response.body().getStatus() == 200) {
                            if (response.body()
                                    .getDataModel().getSubscriptionStatus() != null) {
                                String subscriptionStatus = response.body()
                                        .getDataModel().getSubscriptionStatus();

                                if (subscriptionStatus.equalsIgnoreCase("0")) {
//                                //If not subscribed
                                    navigateToSubscriptionPage();
                                } else {
                                    //If subscribed
                                    navigateToScanPage();
                                }
                            } else {
                                navigateToSubscriptionPage();
                            }
                        } else {
                            navigateToSubscriptionPage();
                        }
                    }

                    @Override
                    public void onError(String message) {
                    }
                });
    }

    private static void getApplicationDetails() {
        SubscriptionService subscriptionService = RetrofitClientInstance
                .getRetrofitInstance().create(SubscriptionService.class);
        Call<GetApplicationDetailsResponse> applicationDetails = subscriptionService
                .getApplicationDetails(ZEROSNAP_TOKEN,
                        LICENCE_KEY);
        RetrofitApiHelper<GetApplicationDetailsResponse> retrofitApiHelper =
                new RetrofitApiHelper<GetApplicationDetailsResponse>();
        retrofitApiHelper.performApiCall(applicationDetails,
                new IRetrofitApiHelper<GetApplicationDetailsResponse>() {
                    @Override
                    public void onSuccess(Response<GetApplicationDetailsResponse> response) {
                        Log.d("TAG", "Response OK ");
                        if (response.body().getStatus() == 200) {
                            licenceKey = response.body().getDataModel().getClientLicenceKey();
                            clientId = response.body().getDataModel().getClientId();
                            checkSubscription();
                        } else {

                        }
                    }

                    @Override
                    public void onError(String message) {
                    }
                });
    }

}
