package com.nanodegree.hyunyong.microdotstatus.data;

import com.google.gson.annotations.SerializedName;

public class Time {
    @SerializedName(value = "s", alternate = "stime")
    private String s;
    private String tz;
    @SerializedName(value = "v", alternate = "vtime")
    private float v;


    // Getter Methods

    public String getS() {
        return s;
    }

    public String getTz() {
        return tz;
    }

    public float getV() {
        return v;
    }

    // Setter Methods

    public void setS(String s) {
        this.s = s;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public void setV(float v) {
        this.v = v;
    }
}
