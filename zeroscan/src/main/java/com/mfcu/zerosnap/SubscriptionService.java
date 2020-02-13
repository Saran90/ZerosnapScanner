package com.mfcu.zerosnap;

import com.mfcu.zerosnap.addSubscription.AddSubscriptionRequest;
import com.mfcu.zerosnap.addSubscription.AddSubscriptionResponse;
import com.mfcu.zerosnap.applicationdetails.GetApplicationDetailsResponse;
import com.mfcu.zerosnap.cancelSubscription.CancelSubscriptionRequest;
import com.mfcu.zerosnap.cancelSubscription.CancelSubscriptionResponse;
import com.mfcu.zerosnap.checkSubscription.CheckSubscriptionResponse;
import com.mfcu.zerosnap.getSubscription.GetSubscriptionResponse;
import com.mfcu.zerosnap.getcurrencies.GetCurrenciesResponse;
import com.mfcu.zerosnap.subscriptionPlans.SubscriptionPlansResponse;
import com.mfcu.zerosnap.verifySubscription.VerifySubscriptionRequest;
import com.mfcu.zerosnap.verifySubscription.VerifySubscriptionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.mfcu.zerosnap.NetworkUtils.ADD_SUSCRIPTION;
import static com.mfcu.zerosnap.NetworkUtils.APPLICATION_DETAILS;
import static com.mfcu.zerosnap.NetworkUtils.CANCEL_SUBSCRIPTION;
import static com.mfcu.zerosnap.NetworkUtils.CHECK_SUSCRIPTION;
import static com.mfcu.zerosnap.NetworkUtils.GET_CURRENCIES;
import static com.mfcu.zerosnap.NetworkUtils.GET_SUBSCRIPTION;
import static com.mfcu.zerosnap.NetworkUtils.NETWORK_AUTHORIZATION;
import static com.mfcu.zerosnap.NetworkUtils.NETWORK_LICENCE_KEY;
import static com.mfcu.zerosnap.NetworkUtils.NETWORK_ZEROSNAP_AUTHORIZATION;
import static com.mfcu.zerosnap.NetworkUtils.SUBSCRIPTIONS;
import static com.mfcu.zerosnap.NetworkUtils.VERIFY_SUSCRIPTION;


public interface SubscriptionService {

    @GET(APPLICATION_DETAILS)
    Call<GetApplicationDetailsResponse> getApplicationDetails(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_LICENCE_KEY) String licenceKey,
            @Query("applicationid") String applicationid
    );

    @GET(CHECK_SUSCRIPTION)
    Call<CheckSubscriptionResponse> checkSubscription(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_LICENCE_KEY) String licenceKey,
            @Query("client_id") String clientId
    );

    @GET(SUBSCRIPTIONS)
    Call<SubscriptionPlansResponse> subscriptionPlans(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_LICENCE_KEY) String licenceKey
    );

    @POST(ADD_SUSCRIPTION)
    Call<AddSubscriptionResponse> addSubscription(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_LICENCE_KEY) String licenceKey,
            @Body AddSubscriptionRequest addSubscriptionRequest
    );

    @POST(VERIFY_SUSCRIPTION)
    Call<VerifySubscriptionResponse> verifySubscription(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken,
            @Header(NETWORK_LICENCE_KEY) String licenceKey,
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

    @GET(GET_CURRENCIES)
    Call<GetCurrenciesResponse> getCurrecncies(
            @Header(NETWORK_ZEROSNAP_AUTHORIZATION) String zerosnapToken
    );
}
