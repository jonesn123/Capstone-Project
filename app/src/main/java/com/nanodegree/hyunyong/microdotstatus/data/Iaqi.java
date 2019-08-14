package com.nanodegree.hyunyong.microdotstatus.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Iaqi {
    MicrodotStatus co;
    MicrodotStatus no2;
    MicrodotStatus o3;
    MicrodotStatus pm10;
    MicrodotStatus pm25;
    MicrodotStatus so2;
    MicrodotStatus wg;

    // Getter Methods

    public MicrodotStatus getCo() {
        return co;
    }

    public MicrodotStatus getNo2() {
        return no2;
    }

    public MicrodotStatus getO3() {
        return o3;
    }

    public MicrodotStatus getPm10() {
        return pm10;
    }

    public MicrodotStatus getPm25() {
        return pm25;
    }

    public MicrodotStatus getSo2() {
        return so2;
    }

    public MicrodotStatus getWg() {
        return wg;
    }

    // Setter Methods

    public void setCo(MicrodotStatus co) {
        this.co = co;
    }

    public void setNo2(MicrodotStatus no2) {
        this.no2 = no2;
    }

    public void setO3(MicrodotStatus o3) {
        this.o3 = o3;
    }

    public void setPm10(MicrodotStatus pm10) {
        this.pm10 = pm10;
    }

    public void setPm25(MicrodotStatus pm25) {
        this.pm25 = pm25;
    }

    public void setSo2(MicrodotStatus so2) {
        this.so2 = so2;
    }

    public void setWg(MicrodotStatus wg) {
        this.wg = wg;
    }
}
