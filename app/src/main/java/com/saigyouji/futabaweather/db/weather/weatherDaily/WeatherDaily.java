package com.saigyouji.futabaweather.db.weather.weatherDaily;

import android.util.Log;

import androidx.room.Entity;

import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.view.QWeather;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.utils.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherDaily implements Serializable {

    private static final String TAG = "WeatherDaily";

    private volatile List<WeatherDailyContent> weatherDailyContents;

    public WeatherDaily(WeatherDailyBean weatherDailyBean, String weatherId) {
        weatherDailyContents = new ArrayList<>();
        var list = weatherDailyBean.getDaily();
        for (WeatherDailyBean.DailyBean d : list
        ) {
            weatherDailyContents.add(new WeatherDailyContent(d, weatherId));
        }
    }
    public WeatherDaily()
    {
        weatherDailyContents = new ArrayList<>();
    }

    public WeatherDaily(String weatherId) {
        weatherDailyContents = new ArrayList<>();
        QWeather.getWeather7D(MyApplication.getContext(), weatherId, new QWeather.OnResultWeatherDailyListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG, "onError: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(WeatherDailyBean weatherDailyBean) {

                var list = weatherDailyBean.getDaily();
                for (WeatherDailyBean.DailyBean d : list
                ) {
                    Log.d(TAG, "onSuccess: " + d.getTextDay());
                    weatherDailyContents.add(new WeatherDailyContent(d, weatherId));
                }
            }
        });
    }

    public List<WeatherDailyContent> getWeatherDailyContents() {
        return weatherDailyContents;
    }

    public void setWeatherDailyContents(List<WeatherDailyContent> weatherDailyContents) {
        this.weatherDailyContents = weatherDailyContents;
    }

}
