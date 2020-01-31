package com.intellilabs.zerosnapscanner;

import com.intellilabs.zerosnapscanner.addSubscription.AddSubscriptionRequest;
import com.intellilabs.zerosnapscanner.addSubscription.AddSubscriptionResponse;
import com.intellilabs.zerosnapscanner.cancelSubscription.CancelSubscriptionRequest;
import com.intellilabs.zerosnapscanner.cancelSubscription.CancelSubscriptionResponse;
import com.intellilabs.zerosnapscanner.checkSubscription.CheckSubscriptionResponse;
import com.intellilabs.zerosnapscanner.getSubscription.GetSubscriptionResponse;
import com.intellilabs.zerosnapscanner.subscriptionPlans.SubscriptionPlansResponse;
import com.intellilabs.zerosnapscanner.verifySubscription.VerifySubscriptionRequest;
import com.intellilabs.zerosnapscanner.verifySubscription.VerifySubscriptionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.intellilabs.zerosnapscanner.NetworkUtils.ADD_SUSCRIPTION;
import static com.intellilabs.zerosnapscanner.NetworkUtils.CANCEL_SUBSCRIPTION;
import static com.intellilabs.zerosnapscanner.NetworkUtils.CHECK_SUSCRIPTION;
import static com.intellilabs.zerosnapscanner.NetworkUtils.GET_SUBSCRIPTION;
import static com.intellilabs.zerosnapscanner.NetworkUtils.NETWORK_AUTHORIZATION;
import static com.intellilabs.zerosnapscanner.NetworkUtils.NETWORK_LICENCE_KEY;
import static com.intellilabs.zerosnapscanner.NetworkUtils.NETWORK_ZEROSNAP_AUTHORIZATION;
import static com.intellilabs.zerosnapscanner.NetworkUtils.SUBSCRIPTIONS;
import static com.intellilabs.zerosnapscanner.NetworkUtils.VERIFY_SUSCRIPTION;

public interface SubscriptionService {

    @GET(CHECK_SUSCRIPTION)
    Call<CheckSubscriptionResponse> checkSubscription(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_LICENCE_KEY) String licenceKey,
            @Header(NETWORK_AUTHORIZATION) String auth,
            @Query("branch_id") String branchId
    );

    @GET(SUBSCRIPTIONS)
    Call<SubscriptionPlansResponse> subscriptionPlans(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_LICENCE_KEY) String licenceKey,
            @Header(NETWORK_AUTHORIZATION) String auth
    );

    @POST(ADD_SUSCRIPTION)
    Call<AddSubscriptionResponse> addSubscription(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_LICENCE_KEY) String licenceKey,
            @Header(NETWORK_AUTHORIZATION) String auth,
            @Body AddSubscriptionRequest addSubscriptionRequest
    );

    @POST(VERIFY_SUSCRIPTION)
    Call<VerifySubscriptionResponse> verifySubscription(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_LICENCE_KEY) String licenceKey,
            @Header(NETWORK_AUTHORIZATION) String auth,
            @Body VerifySubscriptionRequest verifySubscriptionRequest
    );

    @GET(GET_SUBSCRIPTION)
    Call<GetSubscriptionResponse> getSubscription(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_LICENCE_KEY) String licenceKey,
            @Header(NETWORK_AUTHORIZATION) String auth,
            @Query("branch_id") String branchId
    );

    @HTTP(method = "DELETE", path = CANCEL_SUBSCRIPTION, hasBody = true)
    Call<CancelSubscriptionResponse> cancelSubscription(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_AUTHORIZATION) String auth,
            @Header(NETWORK_LICENCE_KEY) String licenceKey,
            @Body CancelSubscriptionRequest request
    );
}
