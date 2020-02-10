package com.mfcu.zerosnap.verifySubscription;

import com.google.gson.annotations.SerializedName;

public class VerifySubscriptionRequest {

    @SerializedName("razorpay_order_id")
    String orderId;

    @SerializedName("razorpay_payment_id")
    String paymentId;

    @SerializedName("razorpay_signature")
    String signature;

    @SerializedName("razorpay_subscription_id")
    String subscriptionId;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
