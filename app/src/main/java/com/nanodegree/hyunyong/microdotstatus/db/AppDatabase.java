package com.nanodegree.hyunyong.microdotstatus.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nanodegree.hyunyong.microdotstatus.data.City;

@Database(entities = {City.class}, version = 1)
@TypeConverters({ConverterFloatToString.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
}
