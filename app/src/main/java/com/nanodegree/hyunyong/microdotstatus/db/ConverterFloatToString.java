package com.nanodegree.hyunyong.microdotstatus.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConverterFloatToString {
    @TypeConverter
    public List<Float> gettingListFromString(String genreIds) {
        List<Float> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Float.parseFloat(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<Float> list) {
        String genreIds = "";
        for (float i : list) {
            genreIds += "," + i;
        }
        return genreIds;
    }
}
