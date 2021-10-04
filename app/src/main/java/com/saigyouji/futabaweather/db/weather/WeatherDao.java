package com.saigyouji.futabaweather.db.weather;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeatherDao
{
    @Query("SELECT * FROM weather_table")
    public LiveData<List<Weather>> getAllWeather();

    @Query("SELECT * FROM weather_table")
    public List<Weather> getAllWeathersByList();

    @Query("SELECT * FROM  weather_table WHERE weatherId IS :weatherId")
    public Weather getWeatherByWeatherId(String weatherId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void update(Weather... weathers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Weather...weather);

    @Delete
    public void delete(Weather weather);
}
