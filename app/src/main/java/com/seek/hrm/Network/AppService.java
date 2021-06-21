package com.seek.hrm.Network;

import com.seek.hrm.Parsing.LoginResponse;
import com.seek.hrm.Parsing.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AppService {
    @POST("api/users/register")
    Call<RegisterResponse> createUser (
          @Body RegisterResponse register
    );

    @POST("api/users/login")
    Call<LoginResponse> login (
           @Body LoginResponse login
    );


}
