package com.nanodegree.hyunyong.microdotstatus.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.nanodegree.hyunyong.microdotstatus.db.ConverterFloatToString;

import java.util.ArrayList;
import java.util.List;

@Entity
public class City {
    @TypeConverters(ConverterFloatToString.class)
    List<Float> geo = new ArrayList<>();
    @PrimaryKey
    @NonNull
    private String name;
    private String url;

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

    public List<Float> getGeo() {
        return geo;
    }

    public void setGeo(List<Float> geo) {
        this.geo = geo;
    }
}
