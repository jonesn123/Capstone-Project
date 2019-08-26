package com.nanodegree.hyunyong.microdotstatus.data;

import java.util.ArrayList;
import java.util.List;

public class MapResponse {
    private String status;
    List<Map> data = new ArrayList<>();
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}