package com.intellilabs.zerosnapscanner.addSubscription;

import com.google.gson.annotations.SerializedName;

public class AddSubscriptionRequest {

    @SerializedName("total_amount")
    String totalAmount;

    @SerializedName("subscription_plan_id")
    String subscriptionPlanId;

    @SerializedName("branch_id")
    String branchId;

    @SerializedName("device_token")
    String deviceToken;

    @SerializedName("device_type")
    String deviceType;

    @SerializedName("scan_count")
    String scanCount;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getScanCount() {
        return scanCount;
    }

    public void setScanCount(String scanCount) {
        this.scanCount = scanCount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public void setSubscriptionPlanId(String subscriptionPlanId) {
        this.subscriptionPlanId = subscriptionPlanId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
