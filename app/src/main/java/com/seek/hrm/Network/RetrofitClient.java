package com.seek.hrm.Network;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private  Retrofit retrofit;
    private  static RetrofitClient instance;
    private static final String BASE_URL = "https://apis-hrm.herokuapp.com/";// http://192.168.43.76:3000
    private  RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static RetrofitClient getInstance() {
        if(instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }
    public AppService getAppService() {
        return retrofit.create(AppService.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
