package com.seek.hrm.api;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.seek.hrm.Activities.LoginActivity;
import com.seek.hrm.Activities.MainActivity;
import com.seek.hrm.Network.IDataResultCallback;
import com.seek.hrm.Network.IResultCallback;
import com.seek.hrm.Network.RetrofitClient;
import com.seek.hrm.POJO.HistoryMeasure;
import com.seek.hrm.Parsing.CreateHistoryResponse;
import com.seek.hrm.Parsing.HistoriesResponse;
import com.seek.hrm.Parsing.LoginResponse;
import com.seek.hrm.Session.LoginSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryService {
   public static void  createHistory(int result, IResultCallback callback) {
       Call<CreateHistoryResponse> call = RetrofitClient.getInstance().getAppService().createHistory(result);
       call.enqueue(new Callback<CreateHistoryResponse>() {
           @Override
           public void onResponse(Call<CreateHistoryResponse> call, Response<CreateHistoryResponse> response) {
               if (response.isSuccessful()) {
                    callback.onSuccess(true);
               } else {
                   callback.onSuccess(false);
               }
           }

           @Override
           public void onFailure(Call<CreateHistoryResponse> call, Throwable t) {
               callback.onSuccess(false);

           }
       });
   }

    public static void  getHistory( IDataResultCallback<List<HistoryMeasure>> callback) {
        Call<HistoriesResponse> call = RetrofitClient.getInstance().getAppService().getHistoryList();
        call.enqueue(new Callback<HistoriesResponse>() {
            @Override
            public void onResponse(Call<HistoriesResponse> call, Response<HistoriesResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(true,response.body().getHistoryMeasure());
                } else {
                    callback.onSuccess(false,null);
                }
            }

            @Override
            public void onFailure(Call<HistoriesResponse> call, Throwable t) {
                callback.onSuccess(false,null);

            }
        });
    }
}
