package com.saigyouji.futabaweather.db.country;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "country_table")
public class Country
{
    private static final String TAG = "Country";

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "weatherId")
    private String weatherId;
    @ColumnInfo(name = "countryName")
    private String countryName;
    @ColumnInfo(name = "provinceName")
    private String provinceName;
    @ColumnInfo(name = "cityName")
    private String cityName;

    public Country()
    {
    }
    @Ignore
    public Country(@NonNull String weatherId,  String provinceName, String cityName, String countryName) {
        this.weatherId = weatherId;
        this.countryName = countryName;
        this.provinceName = provinceName;
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getWeatherId() {
        return weatherId;
    }


    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
}
