package com.seek.hrm.Parsing;

import com.google.gson.annotations.SerializedName;

public class DataResponse {
    @SerializedName("_id")
    private String userId;
    @SerializedName("error")
    private String error;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}