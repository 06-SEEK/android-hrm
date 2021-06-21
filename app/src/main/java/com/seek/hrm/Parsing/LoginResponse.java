package com.seek.hrm.Parsing;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
//    @SerializedName("status")
//    private Object status;
    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private DataResponse data;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String token;


    public LoginResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

//    public Object getStatus() {
//        return status;
//    }

    public DataResponse getData() {
        return data;
    }

    public void setData(DataResponse data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
