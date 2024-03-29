package com.nanodegree.hyunyong.microdotstatus.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private Webservice webservice;

    public Repository(Webservice webservice) {
        this.webservice = webservice;
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

    public LiveData<CitiesResponse> getFeedFromCity(String keyword) {
        final MutableLiveData<CitiesResponse> data = new MutableLiveData<>();

        webservice.getFeedByKeyword(keyword).enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(Call<CitiesResponse> call, Response<CitiesResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CitiesResponse> call, Throwable t) {
                Log.d("TAG", "fail");
            }
        });
        return data;
    }

    public LiveData<MapResponse> getMapInformation(String latlng) {
        final MutableLiveData<MapResponse> data = new MutableLiveData<>();

        webservice.getMapInformation(latlng).enqueue(new Callback<MapResponse>() {
            @Override
            public void onResponse(Call<MapResponse> call, Response<MapResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MapResponse> call, Throwable t) {
                Log.d("TAG", "fail");
            }
        });
        return data;
    }
}
