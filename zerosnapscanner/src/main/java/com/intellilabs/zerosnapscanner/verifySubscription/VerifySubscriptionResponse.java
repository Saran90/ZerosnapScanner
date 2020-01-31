package com.intellilabs.zerosnapscanner.verifySubscription;

import com.google.gson.annotations.SerializedName;

public class VerifySubscriptionResponse {

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
