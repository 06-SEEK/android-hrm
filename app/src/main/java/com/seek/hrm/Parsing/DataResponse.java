package com.seek.hrm.Parsing;

import com.google.gson.annotations.SerializedName;

public class DataResponse {
    @SerializedName("email")
    private String email;
    @SerializedName("error")
    private String error;
    @SerializedName("token")
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}