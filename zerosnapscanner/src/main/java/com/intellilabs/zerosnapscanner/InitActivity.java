package com.intellilabs.zerosnapscanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.intellilabs.zerosnapscanner.applicationdetails.GetApplicationDetailsResponse;
import com.intellilabs.zerosnapscanner.checkSubscription.CheckSubscriptionResponse;

import retrofit2.Call;
import retrofit2.Response;

import static com.intellilabs.zerosnapscanner.SubscriptionActivity.LICENCE_KEY;
import static com.intellilabs.zerosnapscanner.SubscriptionActivity.ZEROSNAP_TOKEN;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_DOCUMENT_TYPE;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_LICENCE_KEY;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_USER_ID;

public class InitActivity extends Activity {

    private static final int SUBSCRIPTION_REQUEST_CODE = 123;

    private static ZerosnapScannerCallback mZerosnapScannerCallback;
    private static String userId;
    private static String applicationId;
    private static ZerosnapScannerType mZerosnapScannerType;
    private String licenceKey;
    private String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        getApplicationDetails();
    }

    /**
     *
     * @param context
     * @param zerosnapScannerCallback
     * @param zerosnapScannerType - Scanning type
     * @param userid - id of user
     * @param applicationid - application application id
     * @return
     */
    public static Intent newIntent(Context context,
                                   ZerosnapScannerCallback zerosnapScannerCallback,
                                   ZerosnapScannerType zerosnapScannerType,
                                   String userid,
                                   String applicationid){
        mZerosnapScannerCallback = zerosnapScannerCallback;
        mZerosnapScannerType = zerosnapScannerType;
        userId = userid;
        applicationId = applicationid;
        return new Intent(context,InitActivity.class);
    }

    /**
     * Remote call for checking the subscription of the logged user.
     */
    private void checkSubscription() {
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
//                                    navigateToSubscriptionPage();
                                    navigateToScanPage();
                                } else {
                                    //If subscribed
                                    navigateToScanPage();
                                }
                            } else {
//                                navigateToSubscriptionPage();
                                navigateToScanPage();
                            }
                        } else {
//                            navigateToSubscriptionPage();
                            navigateToScanPage();
                        }
                    }

                    @Override
                    public void onError(String message) {
                    }
                });
    }

    /**
     * Redirect to scan page.
     */
    private void navigateToScanPage(){
        Intent intent = ZerosnapScannerActivity.newIntent(this,mZerosnapScannerCallback);
        intent.putExtra(EXTRA_DOCUMENT_TYPE,mZerosnapScannerType.name());
        intent.putExtra(EXTRA_LICENCE_KEY,licenceKey);
        intent.putExtra(EXTRA_USER_ID,userId);
        startActivity(intent);
        finish();
    }

    /**
     * Redirect to subscription page
     */
    private void navigateToSubscriptionPage(){
        Intent intent1 = new Intent(this,SubscriptionActivity.class);
        intent1.putExtra(EXTRA_USER_ID,userId);
        intent1.putExtra(EXTRA_LICENCE_KEY,licenceKey);
        intent1.putExtra(EXTRA_DOCUMENT_TYPE,mZerosnapScannerType.name());
        startActivityForResult(intent1,SUBSCRIPTION_REQUEST_CODE);
    }

    /**
     * Get application details corresponding to the application id
     */
    private void getApplicationDetails() {
        SubscriptionService subscriptionService = RetrofitClientInstance
                .getRetrofitInstance().create(SubscriptionService.class);
        Call<GetApplicationDetailsResponse> applicationDetails = subscriptionService
                .getApplicationDetails(ZEROSNAP_TOKEN,
                        LICENCE_KEY,applicationId);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SUBSCRIPTION_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                navigateToScanPage();
            }else {
                finish();
            }
        }
    }
}
