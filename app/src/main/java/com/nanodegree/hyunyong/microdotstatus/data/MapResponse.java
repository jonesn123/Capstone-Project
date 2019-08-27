package com.nanodegree.hyunyong.microdotstatus.data;

import java.util.ArrayList;
import java.util.List;

public class MapResponse {
    private String status;

    public List<Map> getData() {
        return data;
    }

    public void setData(List<Map> data) {
        this.data = data;
    }

    List<Map> data = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}