package com.seek.hrm.Network;

import com.seek.hrm.POJO.HistoryMeasure;
import com.seek.hrm.Parsing.CreateHistoryResponse;
import com.seek.hrm.Parsing.HistoriesResponse;
import com.seek.hrm.Parsing.LoginResponse;
import com.seek.hrm.Parsing.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppService {
    @POST("/api/users/register")
    Call<RegisterResponse> createUser (
          @Body RegisterResponse register
    );

    @POST("/api/users/login")
    Call<LoginResponse> login (
           @Body LoginResponse login
    );

    @GET("/api/histories")
    Call<HistoriesResponse> getHistoryList ();

    @FormUrlEncoded

    @POST("/api/histories")
    Call<CreateHistoryResponse> createHistory (
            @Field("result") int result
    );


}
