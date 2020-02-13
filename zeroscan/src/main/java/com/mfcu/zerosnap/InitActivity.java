package com.mfcu.zerosnap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mfcu.zerosnap.applicationdetails.GetApplicationDetailsResponse;
import com.mfcu.zerosnap.checkSubscription.CheckSubscriptionResponse;
import com.mfcu.zerosnap.getcurrencies.GetCurrenciesResponse;
import com.mfcu.zerosnap.verifySubscription.VerifySubscriptionResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.mfcu.zerosnap.SubscriptionActivity.LICENCE_KEY;
import static com.mfcu.zerosnap.SubscriptionActivity.ZEROSNAP_TOKEN;
import static com.mfcu.zerosnap.ZerosnapScannerUtils.EXTRA_DOCUMENT_TYPE;
import static com.mfcu.zerosnap.ZerosnapScannerUtils.EXTRA_LICENCE_KEY;
import static com.mfcu.zerosnap.ZerosnapScannerUtils.EXTRA_USER_ID;


public class InitActivity extends Activity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    private static final int SUBSCRIPTION_REQUEST_CODE = 123;

    private static ZerosnapScannerCallback mZerosnapScannerCallback;
    private static String userId;
    private static String applicationId;
    private static ZerosnapScannerType mZerosnapScannerType;
    private String licenceKey;
    private String clientId;
    private RelativeLayout mProgressRelativeLayout;
    private RelativeLayout mCurrencyRelativeLayout;
    private Spinner mCurrencySpinner;
    private Button mSubmit;
    String[] currencies;
    private String selectedCurrency;
    private List<GetCurrenciesResponse.Data> currenciresModel = new ArrayList<>();
    private CurrencyListSpinnerAdapter currencyListSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        mProgressRelativeLayout = findViewById(R.id.rl_progress);
        mCurrencyRelativeLayout = findViewById(R.id.rl_currency);
        mCurrencySpinner = findViewById(R.id.sp_currency);
        mSubmit = findViewById(R.id.btn_submit);
        mSubmit.setOnClickListener(this);
        mCurrencySpinner.setOnItemSelectedListener(this);
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
        return new Intent(context, InitActivity.class);
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
                                    getCurrencies();
//                                    navigateToSubscriptionPage();
                                } else {
                                    //If subscribed
                                    navigateToScanPage();
                                }
                            } else {
                                getCurrencies();
//                                navigateToSubscriptionPage();
                            }
                        } else {
                            getCurrencies();
//                            navigateToSubscriptionPage();
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
        Intent intent1 = new Intent(this, SubscriptionActivity.class);
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

    private void showLoading(){
        mProgressRelativeLayout.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        mProgressRelativeLayout.setVisibility(View.GONE);
    }

    private void showCurrencyView(){
        mCurrencyRelativeLayout.setVisibility(View.VISIBLE);
    }

    private void hideCurrencyView(){
        mCurrencyRelativeLayout.setVisibility(View.GONE);
    }

    public void getCurrencies(){
        showLoading();
        hideCurrencyView();
        SubscriptionService subscriptionService = RetrofitClientInstance
                .getRetrofitInstance().create(SubscriptionService.class);
        Call<GetCurrenciesResponse> getCurrenciesResponseCall = subscriptionService
                .getCurrecncies(ZEROSNAP_TOKEN);
        RetrofitApiHelper<GetCurrenciesResponse> retrofitApiHelper =
                new RetrofitApiHelper<>();
        retrofitApiHelper.performApiCall(getCurrenciesResponseCall,
                new IRetrofitApiHelper<GetCurrenciesResponse>() {
                    @Override
                    public void onSuccess(Response<GetCurrenciesResponse> response) {
                        Log.d("TAG", "Response OK ");
                        if (response.body().getStatus() == 200) {
                           hideLoading();
                            currenciresModel = response.body().getDatas();
                            currencies = new String[currenciresModel.size()];
                            for (int i = 0;i<currenciresModel.size();i++){
                                currencies[i] = currenciresModel.get(i).getCurrencyName();
                            }
                            setSpinnerAdapter();
                        } else if (response.body().getStatus() == 400) {
                            Toast.makeText(InitActivity.this,
                                    response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            hideLoading();
                            setResult(RESULT_CANCELED);
                            finish();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        hideLoading();
                        Toast.makeText(InitActivity.this,
                                message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCurrency = String.valueOf(parent.getAdapter().getItem(position));
        Log.d("Selected currency: ",selectedCurrency);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setSpinnerAdapter(){
        currencyListSpinnerAdapter = new CurrencyListSpinnerAdapter(this, currenciresModel);
        mCurrencySpinner.setAdapter(currencyListSpinnerAdapter);
        showCurrencyView();
    }

    @Override
    public void onClick(View v) {
        navigateToSubscriptionPage();
    }
}
