package com.saigyouji.futabaweather.db.country;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

@Dao
public interface CountryDao
{
    @Query("SELECT * FROM country_table")
    public LiveData<List<Country>> getAllCountries();


    @Query("SELECT * FROM country_table WHERE countryName LIKE :countryName")
    public LiveData<List<Country>> getCountriesByCountryName(String countryName);

    @Query("SELECT * FROM country_table WHERE weatherId IS :weatherId")
    public Country getCountryBYWeatherId(String weatherId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Country... countries);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(List<Country> countries);

    @Delete
    public void Delete (Country...countries);
}
