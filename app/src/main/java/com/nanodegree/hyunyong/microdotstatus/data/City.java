package com.nanodegree.hyunyong.microdotstatus.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.nanodegree.hyunyong.microdotstatus.db.ConverterDoubleToString;

import java.util.ArrayList;
import java.util.List;

@Entity
public class City {
    @TypeConverters(ConverterDoubleToString.class)
    List<Double> geo = new ArrayList<>();
    @PrimaryKey
    @NonNull
    private String name;
    private String url;

    public boolean isWidget() {
        return isWidget;
    }

    public void setWidget(boolean widget) {
        isWidget = widget;
    }

    private boolean isWidget;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Double> getGeo() {
        return geo;
    }

    public void setGeo(List<Double> geo) {
        this.geo = geo;
    }
}
