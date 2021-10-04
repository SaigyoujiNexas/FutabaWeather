package com.saigyouji.futabaweather.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.saigyouji.futabaweather.db.country.Country;
import com.saigyouji.futabaweather.repository.CountryRepository;

import java.util.List;

public class CountryViewModel extends AndroidViewModel
{
    private CountryRepository repository;
    private List<Country> AllCountries;


    public CountryViewModel(@NonNull Application application) {
        super(application);
        repository = new CountryRepository(application);
        AllCountries = repository.getAllCountries();
    }

    public List<Country> getAllCountries()
    {
        return repository.getAllCountries();
    }


    public List<Country> getCountriesByName(String name)
    {
        return repository.getCountriesByCountryName(name);
    }

    public Country getCountryByWeatherId(String weatherId)
    {
        return repository.getCountryByWeatherId(weatherId);
    }
    public void insert(Country...countries)
    {
        repository.insert(countries);
    }
    public void delete(Country... countries)
    {
        repository.delete(countries);
    }

}
