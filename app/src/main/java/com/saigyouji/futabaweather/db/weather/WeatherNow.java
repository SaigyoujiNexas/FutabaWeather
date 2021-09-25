package com.saigyouji.futabaweather.db.weather;

import android.util.Log;

import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;
import com.saigyouji.futabaweather.utils.MyApplication;

import java.io.Serializable;

public class WeatherNow implements Serializable
{
    private static final String TAG = "WeatherNow";
    private String ObsTime;
    private String temperature;
    private String icon;
    private String text;
    public WeatherNow()
    {

    }
    public WeatherNow(WeatherNowBean weatherNowBean)
    {
        var now = weatherNowBean.getNow();
        ObsTime = now.getObsTime();
        temperature = now.getTemp();
        icon = now.getIcon();
        text  = now.getText();
    }

    public WeatherNow(String weatherId)
    {
        WeatherNowBean weatherNowBea = null;
        QWeather.getWeatherNow(MyApplication.getContext(), weatherId, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.w(TAG, "onError: WeatherNow: "+ throwable.getMessage());
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean)
            {
                var now  = weatherNowBean.getNow();
                ObsTime = now.getObsTime();
                temperature = now.getTemp();
                icon = now.getIcon();
                text = now.getText();
            }
        });
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setObsTime(String obsTime) {
        ObsTime = obsTime;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getObsTime() {
        return ObsTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getText() {
        return text;
    }
}
