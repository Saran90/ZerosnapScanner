package com.mfcu.zerosnap.checkSubscription;

import com.google.gson.annotations.SerializedName;

public class CheckSubscriptionResponse {

    @SerializedName("status")
    int status;

    @SerializedName("status_message")
    String statusMessage;

    @SerializedName("data")
    DataModel dataModel;

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

    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public class DataModel{
        @SerializedName("subscription_id")
        String subscriptionId;

        @SerializedName("subscription_date")
        String subscriptionDate;

        @SerializedName("total_price")
        String totalPrice;

        @SerializedName("subscription_status")
        String subscriptionStatus;

        public String getSubscriptionStatus() {
            return subscriptionStatus;
        }

        public void setSubscriptionStatus(String subscriptionStatus) {
            this.subscriptionStatus = subscriptionStatus;
        }

        public String getSubscriptionId() {
            return subscriptionId;
        }

        public void setSubscriptionId(String subscriptionId) {
            this.subscriptionId = subscriptionId;
        }

        public String getSubscriptionDate() {
            return subscriptionDate;
        }

        public void setSubscriptionDate(String subscriptionDate) {
            this.subscriptionDate = subscriptionDate;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }
    }
}
