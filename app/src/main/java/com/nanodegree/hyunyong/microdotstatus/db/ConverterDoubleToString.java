package com.nanodegree.hyunyong.microdotstatus.db;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class ConverterDoubleToString {
    @TypeConverter
    public List<Double> gettingListFromString(String genreIds) {
        List<Double> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Double.parseDouble(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<Double> list) {
        String genreIds = "";
        for (double i : list) {
            genreIds += "," + i;
        }
        return genreIds;
    }
}
