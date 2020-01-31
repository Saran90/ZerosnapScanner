package com.intellilabs.zerosnapscanner.subscriptionPlans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubscriptionPlansResponse {

    @SerializedName("status")
    int status;

    @SerializedName("status_message")
    String statusMessage;

    @SerializedName("data")
    List<DataModel> dataModel;

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

    public List<DataModel> getDataModel() {
        return dataModel;
    }

    public void setDataModel(List<DataModel> dataModel) {
        this.dataModel = dataModel;
    }

    public class DataModel{
        @SerializedName("subscription_plan_id")
        String subscriptionPlanId;

        @SerializedName("subscription_plan_name")
        String subscriptionPlanName;

        @SerializedName("subscribed_amount")
        String subscriptionAmount;

        public String getSubscriptionPlanId() {
            return subscriptionPlanId;
        }

        public void setSubscriptionPlanId(String subscriptionPlanId) {
            this.subscriptionPlanId = subscriptionPlanId;
        }

        public String getSubscriptionPlanName() {
            return subscriptionPlanName;
        }

        public void setSubscriptionPlanName(String subscriptionPlanName) {
            this.subscriptionPlanName = subscriptionPlanName;
        }

        public String getSubscriptionAmount() {
            return subscriptionAmount;
        }

        public void setSubscriptionAmount(String subscriptionAmount) {
            this.subscriptionAmount = subscriptionAmount;
        }
    }
}
