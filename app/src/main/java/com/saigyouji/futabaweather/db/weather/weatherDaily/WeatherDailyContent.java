package com.saigyouji.futabaweather.db.weather.weatherDaily;

import com.qweather.sdk.bean.weather.WeatherDailyBean;

import java.io.Serializable;

public class WeatherDailyContent implements Serializable {
    private String fxDate;
    private String sunRise;
    private String sunSet;
    private String weatherId;
    private String tempMax;
    private String tempMin;
    private String iconDay;
    private String iconNight;
    private String textDay;
    private String textNight;

    public WeatherDailyContent() {
    }

    public WeatherDailyContent(WeatherDailyBean.DailyBean dailyBean, String weatherId) {
        fxDate = dailyBean.getFxDate();
        this.weatherId = weatherId;
        sunRise = dailyBean.getSunrise();
        sunSet = dailyBean.getSunset();
        tempMax = dailyBean.getTempMax();
        tempMin = dailyBean.getTempMin();
        iconDay = dailyBean.getIconDay();
        iconNight = dailyBean.getIconNight();
        textDay = dailyBean.getTextDay();
        textNight = dailyBean.getTextNight();
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

    public String getIconDay() {
        return iconDay;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getIconNight() {
        return iconNight;
    }

    public String getSunRise() {
        return sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTextDay() {
        return textDay;
    }

    public String getTextNight() {
        return textNight;
    }

    public void setFxDate(String fxDate) {
        this.fxDate = fxDate;
    }

    public void setIconDay(String iconDay) {
        this.iconDay = iconDay;
    }

    public void setIconNight(String iconNight) {
        this.iconNight = iconNight;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public void setTextDay(String textDay) {
        this.textDay = textDay;
    }

    public void setTextNight(String textNight) {
        this.textNight = textNight;
    }
}
