package com.mfcu.zerosnap.getSubscription;

import com.google.gson.annotations.SerializedName;

public class GetSubscriptionResponse {

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

        @SerializedName("subscription_type")
        String subscriptionType;

        @SerializedName("subscription_name")
        String subscriptionName;

        @SerializedName("subscription_date")
        String subscriptionDate;

        @SerializedName("total_price")
        String totalPrice;

        @SerializedName("subscription_status")
        String subscriptionStatus;

        @SerializedName("scan_count")
        String scanCount;

        @SerializedName("scanned_count")
        String scannedCount;

        @SerializedName("remaining_scans")
        String remainingScans;

        public String getRemainingScans() {
            return remainingScans;
        }

        public void setRemainingScans(String remainingScans) {
            this.remainingScans = remainingScans;
        }

        public String getSubscriptionId() {
            return subscriptionId;
        }

        public void setSubscriptionId(String subscriptionId) {
            this.subscriptionId = subscriptionId;
        }

        public String getSubscriptionType() {
            return subscriptionType;
        }

        public void setSubscriptionType(String subscriptionType) {
            this.subscriptionType = subscriptionType;
        }

        public String getSubscriptionName() {
            return subscriptionName;
        }

        public void setSubscriptionName(String subscriptionName) {
            this.subscriptionName = subscriptionName;
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

        public String getSubscriptionStatus() {
            return subscriptionStatus;
        }

        public void setSubscriptionStatus(String subscriptionStatus) {
            this.subscriptionStatus = subscriptionStatus;
        }

        public String getScanCount() {
            return scanCount;
        }

        public void setScanCount(String scanCount) {
            this.scanCount = scanCount;
        }

        public String getScannedCount() {
            return scannedCount;
        }

        public void setScannedCount(String scannedCount) {
            this.scannedCount = scannedCount;
        }
    }
}
