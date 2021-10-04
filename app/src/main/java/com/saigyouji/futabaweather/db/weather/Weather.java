package com.saigyouji.futabaweather.db.weather;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.saigyouji.futabaweather.db.weather.weatherDaily.WeatherDaily;
import com.saigyouji.futabaweather.db.weather.weatherHourly.WeatherHourly;

import java.io.Serializable;

@Entity(tableName = "weather_table")
public class Weather implements Serializable
{
    private static final String TAG = "Weather";
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "weatherId")
   private String weatherId;


    @ColumnInfo(name = "countryName")
    private String countryName;
    @ColumnInfo(name = "weatherNow")
   private WeatherNow weatherNow;
    @ColumnInfo(name = "weatherDaily")
   private WeatherDaily weatherDaily;
    @ColumnInfo(name = "weatherHourly")
   private WeatherHourly weatherHourly;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Weather()
    {
        weatherNow = new WeatherNow();
        weatherDaily = new WeatherDaily();
        weatherHourly = new WeatherHourly();
    }
    @Ignore
    public Weather(String weatherId)
    {
        this.weatherId = weatherId;
        weatherHourly = new WeatherHourly(weatherId);
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
  //      Log.d(TAG, "Weather: " + weatherHourly.getWeatherHourlyContentList().get(0).getText());
        weatherDaily = new WeatherDaily(weatherId);
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        weatherNow = new WeatherNow(weatherId);
        try {
            Thread.sleep(500);
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }



    public WeatherHourly getWeatherHourly() {
        return weatherHourly;
    }

    public WeatherDaily getWeatherDaily() {
        return weatherDaily;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public WeatherNow getWeatherNow() {
        return weatherNow;
    }

    public void setWeatherDaily(WeatherDaily weatherDaily) {
        this.weatherDaily = weatherDaily;
    }

    public void setWeatherHourly(WeatherHourly weatherHourly) {
        this.weatherHourly = weatherHourly;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public void setWeatherNow(WeatherNow weatherNow) {
        this.weatherNow = weatherNow;
    }

}

