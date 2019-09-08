package com.nanodegree.hyunyong.microdotstatus.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nanodegree.hyunyong.microdotstatus.data.City;

import java.util.List;

@Dao
public interface CityDao {

    @Query("SELECT * FROM city WHERE isWidget = :isWidget")
    List<City> getCities(boolean isWidget);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(City city);

    @Delete
    void delete(City city);
}
