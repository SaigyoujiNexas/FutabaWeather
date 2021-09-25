package com.saigyouji.futabaweather.db.weather.weatherHourly;

import android.util.Log;

import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.view.QWeather;
import com.saigyouji.futabaweather.db.weather.weatherHourly.WeatherHourlyContent;
import com.saigyouji.futabaweather.utils.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherHourly implements Serializable
{
    private static final String TAG = "WeatherHourly";
    private List<WeatherHourlyContent> weatherHourlyContentList;

    public WeatherHourly()
    {
        weatherHourlyContentList = new ArrayList<>();
    }

    public WeatherHourly(WeatherHourlyBean weatherHourlyBean, String weatherId)
    {
        weatherHourlyContentList = new ArrayList<>();
        var temp = weatherHourlyBean.getHourly();
        for (WeatherHourlyBean.HourlyBean h :
                temp) {
            weatherHourlyContentList.add(new WeatherHourlyContent(h, weatherId));
        }
    }
    public WeatherHourly(String weatherId) {
        weatherHourlyContentList = new ArrayList<>();
        QWeather.getWeather24Hourly(MyApplication.getContext(), weatherId, new QWeather.OnResultWeatherHourlyListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG, "onError: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                for (WeatherHourlyBean.HourlyBean hourlyBean : weatherHourlyBean.getHourly()
                ) {
                    weatherHourlyContentList.add(new WeatherHourlyContent(hourlyBean, weatherId));
                }
            }
        });
    }

    public List<WeatherHourlyContent> getWeatherHourlyContentList() {
        return weatherHourlyContentList;
    }

    public void setWeatherHourlyContentList(List<WeatherHourlyContent> weatherHourlyContentList) {
        this.weatherHourlyContentList = weatherHourlyContentList;
    }

}
