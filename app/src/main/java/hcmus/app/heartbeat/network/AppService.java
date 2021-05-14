package hcmus.app.heartbeat.network;

import hcmus.app.heartbeat.model.LoginResponse;
import hcmus.app.heartbeat.model.RegisterResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppService {
    @POST("/user/register")
    Call<RegisterResponse> createUser (
          @Body RegisterResponse register
    );

    @POST("/user/login")
    Call<LoginResponse> login (
           @Body LoginResponse login
    );


}
