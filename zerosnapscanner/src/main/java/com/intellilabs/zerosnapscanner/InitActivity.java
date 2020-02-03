package com.intellilabs.zerosnapscanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

import static com.intellilabs.zerosnapscanner.SubscriptionActivity.LICENCE_KEY;
import static com.intellilabs.zerosnapscanner.SubscriptionActivity.ZEROSNAP_TOKEN;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
    }

    public static Intent newIntent(Context context, ZerosnapScannerCallback zerosnapScannerCallback){
        mZerosnapScannerCallback = zerosnapScannerCallback;
        return new Intent(context,ZerosnapScannerActivity.class);
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
        intent1.putExtra(EXTRA_DOCUMENT_TYPE,mZerosnapScannerType.name());
        mContext.startActivity(intent1);
    }
}
