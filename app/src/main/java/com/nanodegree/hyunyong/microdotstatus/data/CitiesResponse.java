package com.nanodegree.hyunyong.microdotstatus.data;

import java.util.ArrayList;
import java.util.List;

public class CitiesResponse {
    private String status;
    List<ResponseState> data = new ArrayList<>();
    public List<City> getCities() {
        List<City> cities = new ArrayList<>();
        for(ResponseState response: data) {
            cities.add(response.getData().getCity());
        }
        return cities;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
