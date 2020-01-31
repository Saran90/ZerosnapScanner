package com.intellilabs.zerosnapscanner;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.intellilabs.zerosnapscanner.addSubscription.AddSubscriptionRequest;
import com.intellilabs.zerosnapscanner.addSubscription.AddSubscriptionResponse;
import com.intellilabs.zerosnapscanner.checkSubscription.CheckSubscriptionResponse;
import com.intellilabs.zerosnapscanner.subscriptionPlans.SubscriptionPlansResponse;
import com.intellilabs.zerosnapscanner.verifySubscription.VerifySubscriptionRequest;
import com.intellilabs.zerosnapscanner.verifySubscription.VerifySubscriptionResponse;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SubscriptionActivity extends Activity implements TextWatcher, PaymentResultWithDataListener {

    private static final String TAG = SubscriptionActivity.class.getName();

    private String ANNUAL_SUBSCRIPTION_ID = "2";
    private String MONTHLY_SUBSCRIPTION_ID = "1";
    private String PAY_AS_YOU_GO_SUBSCRIPTION_ID = "3";
    private String DAILY_SUBSCRIPTION_ID = "4";
    private String WEEKLY_SUBSCRIPTION_ID = "5";

    public static final String SUBSCRIPTION_FLOW = "SUBSCRIPTION_FLOW";
    public static final String PACKAGE = "PACKAGE";
    public static final String NORMAL = "NORMAL";

    TextView monthlyTitleTextView;

    TextView yearlyTitleTextView;

    TextView payAsYouGoTitleTextView;

    ExpandableRelativeLayout monthlyExpandableRelativeLayout;

    ExpandableRelativeLayout yearlyExpandableRelativeLayout;

    ExpandableRelativeLayout payAsYouGoExpandableRelativeLayout;

    TextView payAsYouGoPriceTextView;

    TextView monthlyPriceTextView;

    TextView annuallyPriceTextView;

    EditText scanCountEditText;

    Button monthlyPayButton;

    Button yearlyPayButton;

    Button payAsYouGoPayButton;

    private List<SubscriptionPlansResponse.DataModel> subscriptDataModels;
    private String monthlyAmount = "";
    private String annualAmount = "";
    private String dailyAmount = "";
    private String weeklyAmount = "";
    private float scanCount = 0;
    private double payAsYouGoAmount = 0;
    //    private SweetAlertDialog pDialog;
    private String selectedSubscriptionId = "", selectedSubscriptionAmount = "",
            selectedSubscriptionOrderId = "", selectedSubscriptionPaymentId = "",
            selectedSubscriptionSecretId = "";
    private String selectedSubscriptionSubscribeId = "";

    public void onCloseClicked() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onSelectedSubscriptionCloseClicked() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onMonthlyClicked() {
        yearlyExpandableRelativeLayout.collapse();
        payAsYouGoExpandableRelativeLayout.collapse();
        monthlyExpandableRelativeLayout.expand();
        yearlyTitleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_down), null);
        payAsYouGoTitleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_down), null);
        monthlyTitleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_up), null);
    }

    public void onYearlyClicked() {
        monthlyExpandableRelativeLayout.collapse();
        payAsYouGoExpandableRelativeLayout.collapse();
        yearlyExpandableRelativeLayout.expand();
        monthlyTitleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_down), null);
        payAsYouGoTitleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_down), null);
        yearlyTitleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_up), null);
    }

    public void onPayAsYouGoClicked() {
        monthlyExpandableRelativeLayout.collapse();
        yearlyExpandableRelativeLayout.collapse();
        payAsYouGoExpandableRelativeLayout.expand();
        monthlyTitleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_down), null);
        yearlyTitleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_down), null);
        payAsYouGoTitleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_up), null);
    }

    public void onPayAsYouGoPayClicked(View view) {
        for (int i = 0; i < subscriptDataModels.size(); i++) {
            if (subscriptDataModels.get(i).getSubscriptionPlanId()
                    .equalsIgnoreCase(PAY_AS_YOU_GO_SUBSCRIPTION_ID)) {
                selectedSubscriptionId = subscriptDataModels.get(i).getSubscriptionPlanId();
            }
        }
//        if (Double.parseDouble(selectedSubscriptionAmount) != 0)
//            processOrder();
//        else
//            Toast.makeText(this, "Invalid scan count!", Toast.LENGTH_SHORT).show();
    }

    public void onMonthlyPayClicked(View view) {
        scanCount = 0;
        for (int i = 0; i < subscriptDataModels.size(); i++) {
            if (subscriptDataModels.get(i).getSubscriptionPlanId()
                    .equalsIgnoreCase(MONTHLY_SUBSCRIPTION_ID)) {
                selectedSubscriptionId = subscriptDataModels.get(i).getSubscriptionPlanId();
                selectedSubscriptionAmount = subscriptDataModels.get(i).getSubscriptionAmount();
            }
        }
//        processOrder();
    }

    public void onAnnuallyPayClicked() {
        scanCount = 0;
        for (int i = 0; i < subscriptDataModels.size(); i++) {
            if (subscriptDataModels.get(i).getSubscriptionPlanId()
                    .equalsIgnoreCase(ANNUAL_SUBSCRIPTION_ID)) {
                selectedSubscriptionId = subscriptDataModels.get(i).getSubscriptionPlanId();
                selectedSubscriptionAmount = subscriptDataModels.get(i).getSubscriptionAmount();
            }
        }
//        processOrder();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (getIntent().getStringExtra(SUBSCRIPTION_FLOW) != null) {
            loadViewForPackage();
        } else {
//            checkSubscription();
        }
        new FileDeleteAsync().execute();
    }

    private void initViews(){
        monthlyTitleTextView = findViewById(R.id.tv_monthly);
        yearlyTitleTextView = findViewById(R.id.tv_yearly);
        payAsYouGoTitleTextView = findViewById(R.id.tv_pay_as_you_go);
        monthlyExpandableRelativeLayout = findViewById(R.id.erl_monthly);
        yearlyExpandableRelativeLayout = findViewById(R.id.erl_yearly);
        payAsYouGoExpandableRelativeLayout = findViewById(R.id.erl_pay_as_you_go);
        payAsYouGoPriceTextView = findViewById(R.id.tv_pay_as_you_go_price);
        monthlyPriceTextView = findViewById(R.id.tv_monthly_price);
        annuallyPriceTextView = findViewById(R.id.tv_yearly_price);
        scanCountEditText = findViewById(R.id.et_scan_count);
        monthlyPayButton = findViewById(R.id.btn_monthly_pay);
        yearlyPayButton = findViewById(R.id.btn_yearly_pay);
        payAsYouGoPayButton = findViewById(R.id.btn_pay_as_you_go_pay);
    }

    private void deleteZerosnapFolder() {
        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name);
        File file = new File(mPath);
        if (file.isDirectory())
            if (file.exists()) {
                String[] children = file.list();
                if (children != null) {
                    for (int i = 0; i < children.length; i++) {
                        new File(file, children[i]).delete();
                    }
                }
                file.delete();
            }

    }

    public class FileDeleteAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            deleteZerosnapFolder();
            return null;
        }
    }

//    private void checkSubscription() {
//        SubscriptionService subscriptionService = RetrofitClientInstance
//                .getRetrofitInstance().create(SubscriptionService.class);
//        Call<CheckSubscriptionResponse> subscription = subscriptionService
//                .checkSubscription(preferenceHelper.getZerosnapToken(),
//                        preferenceHelper.getLicenceKey(),
//                        preferenceHelper.getToken(), preferenceHelper.getBranch());
//        RetrofitApiHelper<CheckSubscriptionResponse> retrofitApiHelper =
//                new RetrofitApiHelper<CheckSubscriptionResponse>();
//        retrofitApiHelper.performApiCall(subscription,
//                new IRetrofitApiHelper<CheckSubscriptionResponse>() {
//                    @Override
//                    public void onSuccess(Response<CheckSubscriptionResponse> response) {
//                        Log.d("TAG", "Response OK ");
//                        if (response.body().getStatus() == 200) {
//                            if (response.body()
//                                    .getDataModel().getSubscriptionStatus() != null) {
//                                String subscriptionStatus = response.body()
//                                        .getDataModel().getSubscriptionStatus();
//
//                                if (subscriptionStatus.equalsIgnoreCase("0")) {
////                                //If not subscribed
//                                    loadView();
//                                } else {
//                                    //If subscribed
//                                    setResult(RESULT_OK);
//                                    finish();
//                                }
//                            } else {
//                                loadView();
//                            }
//                        } else {
//                            loadView();
//                        }
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                    }
//                });
//    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    public void loadView() {
        setContentView(R.layout.activity_subscription);
//        getSubscriptionPlans();
        yearlyExpandableRelativeLayout.collapse();
        monthlyExpandableRelativeLayout.collapse();
        scanCountEditText.addTextChangedListener(this);
//        initDialog();
        Checkout.preload(getApplicationContext());
    }

    public void loadViewForPackage() {
        setContentView(R.layout.activity_subscription);
//        getSubscriptionPlans();
        yearlyExpandableRelativeLayout.collapse();
        monthlyExpandableRelativeLayout.collapse();
        scanCountEditText.addTextChangedListener(this);
//        initDialog();
        Checkout.preload(getApplicationContext());

        monthlyTitleTextView.setClickable(false);
        yearlyTitleTextView.setClickable(false);
    }

//    public void getSubscriptionPlans() {
//        SubscriptionService subscriptionService = RetrofitClientInstance
//                .getRetrofitInstance().create(SubscriptionService.class);
//        Call<SubscriptionPlansResponse> subscriptionPlans = subscriptionService
//                .subscriptionPlans(preferenceHelper.getZerosnapToken(),
//                        preferenceHelper.getToken(),
//                        preferenceHelper.getLicenceKey());
//        RetrofitApiHelper<SubscriptionPlansResponse> retrofitApiHelper =
//                new RetrofitApiHelper<SubscriptionPlansResponse>();
//        retrofitApiHelper.performApiCall(subscriptionPlans,
//                new IRetrofitApiHelper<SubscriptionPlansResponse>() {
//                    @Override
//                    public void onSuccess(Response<SubscriptionPlansResponse> response) {
//                        Log.d("TAG", "Response OK ");
//                        if (response.body().getStatus() == 200) {
//                            subscriptDataModels =
//                                    response.body().getDataModel();
//                            for (int i = 0; i < subscriptDataModels.size(); i++) {
//                                if (subscriptDataModels.get(i).getSubscriptionPlanId()
//                                        .equalsIgnoreCase("1")) {
//                                    monthlyAmount = subscriptDataModels.get(i).getSubscriptionAmount();
//                                } else if (subscriptDataModels.get(i).getSubscriptionPlanId()
//                                        .equalsIgnoreCase("2")) {
//                                    annualAmount = subscriptDataModels.get(i).getSubscriptionAmount();
//                                } else if (subscriptDataModels.get(i).getSubscriptionPlanId()
//                                        .equalsIgnoreCase("5")) {
//                                    weeklyAmount = subscriptDataModels.get(i).getSubscriptionAmount();
//                                } else if (subscriptDataModels.get(i).getSubscriptionPlanId()
//                                        .equalsIgnoreCase("4")) {
//                                    dailyAmount = subscriptDataModels.get(i).getSubscriptionAmount();
//                                }
//                            }
//
//                            monthlyPriceTextView.setText(getString(R.string.ruppee) + monthlyAmount);
//                            annuallyPriceTextView.setText(getString(R.string.ruppee) + annualAmount);
//                        }
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                    }
//                });
//    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.toString().trim().equalsIgnoreCase("")) {
            payAsYouGoPayButton.setEnabled(false);
            payAsYouGoPayButton.setClickable(false);
        } else {
            payAsYouGoPayButton.setEnabled(true);
            payAsYouGoPayButton.setClickable(true);
            String count = editable.toString().trim();
            scanCount = Float.parseFloat(count);
            if (scanCount > 50) {
                scanCount = 50;
                scanCountEditText.setText("50");
            }
            if (subscriptDataModels != null) {
                for (int i = 0; i < subscriptDataModels.size(); i++) {
                    if (subscriptDataModels.get(i).getSubscriptionPlanId()
                            .equalsIgnoreCase(PAY_AS_YOU_GO_SUBSCRIPTION_ID)) {
                        payAsYouGoAmount = scanCount * Double.parseDouble(subscriptDataModels
                                .get(i).getSubscriptionAmount());
                    }
                }
                payAsYouGoAmount = payAsYouGoAmount + (payAsYouGoAmount * 0.18);
                payAsYouGoAmount = Double.parseDouble(String.format("%.2f", payAsYouGoAmount));
                selectedSubscriptionAmount = String.valueOf(payAsYouGoAmount);
                payAsYouGoPriceTextView.setText(getString(R.string.ruppee) + payAsYouGoAmount);
            }
        }
    }

//    public void processOrder() {
//        showDialog();
//        AddSubscriptionRequest addSubscriptionRequest = new AddSubscriptionRequest();
//        addSubscriptionRequest.setBranchId(preferenceHelper.getBranch());
//        addSubscriptionRequest.setSubscriptionPlanId(selectedSubscriptionId);
//        addSubscriptionRequest.setTotalAmount(selectedSubscriptionAmount);
//        addSubscriptionRequest.setDeviceType("android");
//        addSubscriptionRequest.setDeviceToken(preferenceHelper.getFcmToken());
//        addSubscriptionRequest.setScanCount(String.valueOf(scanCount));
//        SubscriptionService subscriptionService = RetrofitClientInstance
//                .getRetrofitInstance().create(SubscriptionService.class);
//        Call<AddSubscriptionResponse> addSubscription = subscriptionService
//                .addSubscription(preferenceHelper.getZerosnapToken(), preferenceHelper.getLicenceKey(),
//                        preferenceHelper.getToken(), addSubscriptionRequest);
//        RetrofitApiHelper<AddSubscriptionResponse> retrofitApiHelper =
//                new RetrofitApiHelper<>();
//        retrofitApiHelper.performApiCall(addSubscription,
//                new IRetrofitApiHelper<AddSubscriptionResponse>() {
//                    @Override
//                    public void onSuccess(Response<AddSubscriptionResponse> response) {
//                        Log.d("TAG", "Response OK ");
//                        if (response.body().getStatus() == 200) {
//                            selectedSubscriptionOrderId = response.body().getDataModel().getOrderId();
//                            selectedSubscriptionSubscribeId = response.body().getDataModel().getRazorPaySubscriptionId();
//                            startPayment(selectedSubscriptionSubscribeId, selectedSubscriptionOrderId, selectedSubscriptionAmount);
//                        }
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        hideDialog();
//                    }
//                });
//    }

    public void startPayment(String subscriptionId, String orderId, String amount) {
        changeDialogTitle("Configuring Payment...");
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
//        checkout.setImage(R.mipmap.ic_launcher);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Zerosnap");

            /**
             * Description can be anything
             * eg: Order #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Order #123456");
            if (selectedSubscriptionId.equalsIgnoreCase(PAY_AS_YOU_GO_SUBSCRIPTION_ID))
                options.put("order_id", orderId);
            else {
                options.put("subscription_id", subscriptionId);
                options.put("recurring", 1);
            }
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            double amountInt = Double.parseDouble(amount) * 100;
            options.put("amount", String.valueOf(amountInt));

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    public void changeDialogTitle(String title) {
//        baseAlert.updateAlertTitle(title);
    }

    public void showDialog() {
//        baseAlert.showProgressDialog("Processing...");
    }

    public void hideDialog() {
//        baseAlert.hideProgressDialog();
    }

//    public void verifySubscription() {
//        showDialog();
//        VerifySubscriptionRequest verifySubscriptionRequest = new VerifySubscriptionRequest();
//        if (selectedSubscriptionId.equalsIgnoreCase(PAY_AS_YOU_GO_SUBSCRIPTION_ID))
//            verifySubscriptionRequest.setOrderId(selectedSubscriptionOrderId);
//        else
//            verifySubscriptionRequest.setSubscriptionId(selectedSubscriptionSubscribeId);
//        verifySubscriptionRequest.setPaymentId(selectedSubscriptionPaymentId);
//        verifySubscriptionRequest.setSignature(selectedSubscriptionSecretId);
//        SubscriptionService subscriptionService = RetrofitClientInstance
//                .getRetrofitInstance().create(SubscriptionService.class);
//        Call<VerifySubscriptionResponse> verifySubscription = subscriptionService
//                .verifySubscription(preferenceHelper.getZerosnapToken(), preferenceHelper.getLicenceKey(),
//                        preferenceHelper.getToken(), verifySubscriptionRequest);
//        RetrofitApiHelper<VerifySubscriptionResponse> retrofitApiHelper =
//                new RetrofitApiHelper<>();
//        retrofitApiHelper.performApiCall(verifySubscription,
//                new IRetrofitApiHelper<VerifySubscriptionResponse>() {
//                    @Override
//                    public void onSuccess(Response<VerifySubscriptionResponse> response) {
//                        Log.d("TAG", "Response OK ");
//                        if (response.body().getStatus() == 200) {
//                            baseAlert.updateAlertTitle("Verified!");
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    hideDialog();
//                                    finish();
//                                }
//                            }, 2000);
//                        } else if (response.body().getStatus() == 400) {
//                            Toast.makeText(SubscriptionActivity.this,
//                                    response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
//                            hideDialog();
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        hideDialog();
//                        Toast.makeText(SubscriptionActivity.this,
//                                message, Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
//        hideDialog();
//        baseAlert.updateAlertTitle("Verifying Payment...");
//        Log.d(TAG, "Success! " + s);
//        selectedSubscriptionOrderId = paymentData.getOrderId();
//        selectedSubscriptionPaymentId = paymentData.getPaymentId();
//        selectedSubscriptionSecretId = paymentData.getSignature();
//        verifySubscription();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        hideDialog();
        Log.d(TAG, s);
        Log.d(TAG, "Failed! " + s);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideDialog();
    }
}
