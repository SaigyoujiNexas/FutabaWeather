package com.saigyouji.futabaweather.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.saigyouji.futabaweather.db.country.Country;
import com.saigyouji.futabaweather.db.country.CountryDao;
import com.saigyouji.futabaweather.db.country.CountryDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class CountryRepository
{
    private static final String TAG = "CountryRepository";
    private CountryDao countryDao;
    private List<Country> AllCountries;

    public CountryRepository(Application application)
    {
        var db = CountryDatabase.getDatabase(application);
        countryDao = db.countryDao();
        AllCountries = countryDao.getAllCountries();
    }

    public List<Country> getCountriesByCountryName(String countryName)
    {
        return countryDao.getCountriesByCountryName(countryName);
    }
    public Country getCountryByWeatherId(String weatherID)
    {
        return countryDao.getCountryBYWeatherId(weatherID);
    }

    public List<Country> getAllCountries() {
        return AllCountries;
    }
    public void insert(Country... countries)
    {
        CountryDatabase.databaseWriteExecutor.execute(() -> {
            Log.d(TAG, "insert: " + countries[0].getCountryName());
            countryDao.insert(countries);
        });
    }

    public void delete(Country...countries)
    {
        CountryDatabase.databaseWriteExecutor.execute(() -> {
            countryDao.Delete(countries);
        });
    }

}
