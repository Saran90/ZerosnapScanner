package com.mfcu.zerosnap.cancelSubscription;

import com.google.gson.annotations.SerializedName;

public class CancelSubscriptionRequest {

    @SerializedName("subscription_id")
    String subscriptionId;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
