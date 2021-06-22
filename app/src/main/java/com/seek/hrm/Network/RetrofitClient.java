package com.seek.hrm.Network;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
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

    private  RetrofitClient(String accessToken) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader(   "Authorization", accessToken).build();
            return chain.proceed(request);
        });
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
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

    public void setAuthorizationHeader(String accessToken){
        instance  = new RetrofitClient(accessToken);
    }


    public Retrofit getRetrofit() {
        return retrofit;
    }

}
