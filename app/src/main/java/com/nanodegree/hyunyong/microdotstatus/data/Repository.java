package com.nanodegree.hyunyong.microdotstatus.data;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private Webservice webservice;
    private SharedPreferences sharedPreferences;

    public Repository(Webservice webservice, SharedPreferences sharedPreferences) {
        this.webservice = webservice;
        this.sharedPreferences = sharedPreferences;
    }

    public LiveData<ResponseState> getFeedFromLocation(double latitude, double longtitude) {
        final MutableLiveData<ResponseState> data = new MutableLiveData<>();

        webservice.getFeedByLocation(latitude, longtitude).enqueue(new Callback<ResponseState>() {
            @Override
            public void onResponse(Call<ResponseState> call, Response<ResponseState> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseState> call, Throwable t) {
                Log.d("location ", t.getMessage());
            }
        });

        return data;
    }
}
