package com.mfcu.zerosnap.cancelSubscription;

import com.google.gson.annotations.SerializedName;

public class CancelSubscriptionResponse {

    @SerializedName("status")
    int status;

    @SerializedName("status_message")
    String statusMessage;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
