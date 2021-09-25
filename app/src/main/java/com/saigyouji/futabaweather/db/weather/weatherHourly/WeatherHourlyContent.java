package com.saigyouji.futabaweather.db.weather.weatherHourly;

import com.qweather.sdk.bean.weather.WeatherHourlyBean;

import java.io.Serializable;

public class WeatherHourlyContent implements Serializable {
    private String fxDate;
    private String temperature;
    private String icon;
    private String text;
    private String weatherId;

    public WeatherHourlyContent(WeatherHourlyBean.HourlyBean hourlyBean, String weatherId)
    {
        this.weatherId =  weatherId;
        fxDate = hourlyBean.getFxTime();
        temperature = hourlyBean.getTemp();
        icon = hourlyBean.getIcon();
        text = hourlyBean.getText();
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getFxDate() {
        return fxDate;
    }

    public String getText() {
        return text;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setFxDate(String fxDate) {
        this.fxDate = fxDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
