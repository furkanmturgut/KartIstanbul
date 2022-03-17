
package com.kunai.istanbulkart.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhoneLoginModel {

    String response;

    public PhoneLoginModel(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
