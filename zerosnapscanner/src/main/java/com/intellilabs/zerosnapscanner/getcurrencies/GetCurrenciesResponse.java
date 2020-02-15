package com.intellilabs.zerosnapscanner.getcurrencies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by uvionics on 13,February,2020
 */
public class GetCurrenciesResponse {

    @SerializedName("status")
    Integer status;

    @SerializedName("status_message")
    String statusMessage;

    @SerializedName("data")
    List<Data> datas;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public class Data{

        @SerializedName("currency_code")
        String currencyCode;

        @SerializedName("currency_name")
        String currencyName;

        @SerializedName("currency_symbol")
        String currencySymbol;

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public String getCurrencySymbol() {
            return currencySymbol;
        }

        public void setCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
        }
    }
}
