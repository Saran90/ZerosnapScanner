package com.intellilabs.zerosnapscanner.addSubscription;

import com.google.gson.annotations.SerializedName;

public class AddSubscriptionResponse {

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

        @SerializedName("order_id")
        String orderId;

        @SerializedName("razorpay_subscription_id")
        String razorPaySubscriptionId;

        public String getRazorPaySubscriptionId() {
            return razorPaySubscriptionId;
        }

        public void setRazorPaySubscriptionId(String razorPaySubscriptionId) {
            this.razorPaySubscriptionId = razorPaySubscriptionId;
        }

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
    }
}
