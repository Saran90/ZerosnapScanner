package com.mfcu.zerosnap.applicationdetails;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Saran M S on 2/1/2020.
 */
public class GetApplicationDetailsResponse {

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
        @SerializedName("client_id")
        String clientId;

        @SerializedName("client_name")
        String clientName;

        @SerializedName("client_address")
        String clientAddress;

        @SerializedName("client_email")
        String clientEmail;

        @SerializedName("client_country")
        String clientCountry;

        @SerializedName("client_contact")
        String clientContact;

        @SerializedName("client_application_id")
        String clientApplicationId;

        @SerializedName("client_licencekey")
        String clientLicenceKey;

        @SerializedName("licence_start_date")
        String clientLicenceStartDate;

        @SerializedName("licence_end_date")
        String clientLicenceEndDate;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getClientAddress() {
            return clientAddress;
        }

        public void setClientAddress(String clientAddress) {
            this.clientAddress = clientAddress;
        }

        public String getClientEmail() {
            return clientEmail;
        }

        public void setClientEmail(String clientEmail) {
            this.clientEmail = clientEmail;
        }

        public String getClientCountry() {
            return clientCountry;
        }

        public void setClientCountry(String clientCountry) {
            this.clientCountry = clientCountry;
        }

        public String getClientContact() {
            return clientContact;
        }

        public void setClientContact(String clientContact) {
            this.clientContact = clientContact;
        }

        public String getClientApplicationId() {
            return clientApplicationId;
        }

        public void setClientApplicationId(String clientApplicationId) {
            this.clientApplicationId = clientApplicationId;
        }

        public String getClientLicenceKey() {
            return clientLicenceKey;
        }

        public void setClientLicenceKey(String clientLicenceKey) {
            this.clientLicenceKey = clientLicenceKey;
        }

        public String getClientLicenceStartDate() {
            return clientLicenceStartDate;
        }

        public void setClientLicenceStartDate(String clientLicenceStartDate) {
            this.clientLicenceStartDate = clientLicenceStartDate;
        }

        public String getClientLicenceEndDate() {
            return clientLicenceEndDate;
        }

        public void setClientLicenceEndDate(String clientLicenceEndDate) {
            this.clientLicenceEndDate = clientLicenceEndDate;
        }
    }
}
