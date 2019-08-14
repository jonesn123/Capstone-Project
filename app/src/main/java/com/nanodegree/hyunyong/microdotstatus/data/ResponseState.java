package com.nanodegree.hyunyong.microdotstatus.data;

public class ResponseState {
    private String status;
    Microdot data;

    public String getStatus() {
        return status;
    }

    public Microdot getData() {
        return data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(Microdot data) {
        this.data = data;
    }
}
