package com.nanodegree.hyunyong.microdotstatus.data;

import java.util.ArrayList;
import java.util.List;

public class CitiesResponse {
    private String status;
    List<CityInformation> data = new ArrayList<>();
    public List<City> getCities() {
        List<City> cities = new ArrayList<>();
        for(CityInformation response: data) {
            cities.add(response.getCity());
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
