package com.saigyouji.futabaweather.db.weather;



import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.saigyouji.futabaweather.db.weather.weatherDaily.WeatherDaily;
import com.saigyouji.futabaweather.db.weather.weatherHourly.WeatherHourly;


public class Converter
{
    @TypeConverter
    public static String fromWeatherToString(Weather weather)
    {
        var gson = new Gson();
        return gson.toJson(weather);
    }
    @TypeConverter
    public static Weather fromStringToWeather(String str)
    {
        var gson = new Gson();
        return(Weather) gson.fromJson(str, Weather.class);
    }
    @TypeConverter
    public static WeatherHourly fromStringToWeatherHourly(String str)
    {
        var gson = new Gson();
        return (WeatherHourly) gson.fromJson(str, WeatherHourly.class);
    }
    @TypeConverter
    public static String fromWeatherHourlyToString(WeatherHourly weatherHourly)
    {
        return new Gson().toJson(weatherHourly);
    }
    @TypeConverter
    public static WeatherNow fromStringToWeatherNow(String str)
    {
        return (WeatherNow) new Gson().fromJson(str, WeatherNow.class);
    }
    @TypeConverter
    public static String fromWeatherNowToString(WeatherNow weatherNow)
    {
        return new Gson().toJson(weatherNow);
    }
    @TypeConverter
    public static WeatherDaily fromStringToWeatherDaily(String str)
    {
        return  (WeatherDaily)new Gson().fromJson(str, WeatherDaily.class);
    }

    @TypeConverter
    public static String  fromWeatherDailyToString (WeatherDaily weatherDaily)
    {
        return  new Gson().toJson(weatherDaily);
    }
}
