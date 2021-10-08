package com.saigyouji.futabaweather.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.repository.WeatherRepository;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel
{
    private WeatherRepository weatherRepository;
    private LiveData<List<Weather>> AllWeathers;


    public WeatherViewModel(Application application)
    {
        super(application);
        weatherRepository = new WeatherRepository(application);
        AllWeathers = weatherRepository.getAllWeathers();
    }

    public WeatherRepository getWeatherRepository() {
        return weatherRepository;
    }
    public void delete(Weather...weathers)
    {
        weatherRepository.delete(weathers);
    }

    public LiveData<List<Weather>> getAllWeathers() {
        return AllWeathers;
    }
    public List<Weather> getAllWeathersByList()
    {
        return weatherRepository.getAllWeathersByList();
    }


    public void insert(Weather...weathers)
    {
        weatherRepository.insert(weathers);
    }

    public void insertWeatherFromInternet(String weatherId)
    {
        weatherRepository.insertWeatherFromInternet(weatherId);
    }

    public void updateWeatherFromInternet(Weather weather)
    {
        weatherRepository.updateWeatherFromInternet(weather);
    }
    public void updateWeathersFromInternet(List<Weather> weathers)
    {
        weatherRepository.update(weathers);
    }
}
