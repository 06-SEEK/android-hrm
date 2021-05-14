package hcmus.app.heartbeat.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName( "status" )
    private String status;
    @SerializedName( "data" )
    private DataResponse data;
    @SerializedName( "email" )
    private String email;
    @SerializedName( "password" )
    private String  password;

    public RegisterResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataResponse getData() {
        return data;
    }

    public void setData(DataResponse data) {
        this.data = data;
    }
}
