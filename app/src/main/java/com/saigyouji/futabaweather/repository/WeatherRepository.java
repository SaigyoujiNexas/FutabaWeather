package com.saigyouji.futabaweather.repository;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;
import com.saigyouji.futabaweather.db.country.CountryDatabase;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.db.weather.WeatherDao;
import com.saigyouji.futabaweather.db.weather.WeatherDatabase;
import com.saigyouji.futabaweather.db.weather.WeatherNow;
import com.saigyouji.futabaweather.db.weather.weatherDaily.WeatherDaily;
import com.saigyouji.futabaweather.db.weather.weatherHourly.WeatherHourly;
import com.saigyouji.futabaweather.service.LocationService;
import com.saigyouji.futabaweather.utils.ContentUtil;
import com.saigyouji.futabaweather.utils.MyApplication;
import com.saigyouji.futabaweather.utils.ThreadPool;

import java.util.List;

public class WeatherRepository {
    private static final String TAG = "WeatherRepository";
    private LiveData<List<Weather>> AllWeathers;
    private WeatherDao weatherDao;
    private CountryRepository countryRepository;


    /**
     * handler message number
     */
    private static final int WEATHER_GET_NOW = 1;
    private static final int WEATHER_GET_DAILY = 2;
    private static final int WEATHER_GET_HOURLY = 3;
    private static final int WEATHER_GET_COUNTRY = 4;

    private Handler handler;


    public WeatherRepository(Application application) {
        var db = WeatherDatabase.getWeatherDatabase(application);
        weatherDao = db.weatherDao();
        AllWeathers = weatherDao.getAllWeather();
        countryRepository = new CountryRepository(application);
        //    list = weatherDao.getAllWeathersByList();
    }

    public LiveData<List<Weather>> getAllWeathers() {
        return AllWeathers;
    }

    public List<Weather> getAllWeathersByList() {
        return weatherDao.getAllWeathersByList();
    }

    public void insert(Weather... weathers) {
        WeatherDatabase.databaseWriteExecutor.execute(() -> {
            weatherDao.insert(weathers);
            Log.d(TAG, "insert: " + weathers[0].getWeatherId());
        });
    }

    public void update(Weather... weathers) {
        WeatherDatabase.databaseWriteExecutor.execute(() -> {
            weatherDao.update(weathers);
            Log.d(TAG, "update: " + weathers[0].getWeatherId());
        });
    }

    public void update(List<Weather> weathers) {
        MyApplication.getApplication().startService
                (new Intent(MyApplication.getContext(), LocationService.class));
        for (int i = 0; i < weathers.size(); i++) {
            Weather w = weathers.get(i);
            Log.d(TAG, "updateWeathers: the item is" + w.getCountryName());
            updateWeatherFromInternet(w);
        }
        Log.d(TAG, "updateWeathers: update complete");
    }

    public void updateWeatherFromInternet(Weather weather) {
        final String weatherId;
        String countryName = weather.getCountryName();
        if (weather.getWeatherId().equals("auto")) {
            weatherId = ContentUtil.getNowLon() + "," + ContentUtil.getNowLat();
            countryName = ContentUtil.getNowCountryName();
            weather.setCountryName(countryName);
        } else {
            weatherId = weather.getWeatherId();
        }
        QWeather.getWeatherNow(MyApplication.getContext(), weatherId, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.w(TAG, "onError: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean) {
                Log.d(TAG, "onSuccess: weatherNow");
                weather.setWeatherNow(new WeatherNow(weatherNowBean));
                QWeather.getWeather24Hourly(MyApplication.getContext(), weatherId, new QWeather.OnResultWeatherHourlyListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.w(TAG, "onError: " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                        Log.d(TAG, "onSuccess: weatherHourly");
                        weather.setWeatherHourly(new WeatherHourly(weatherHourlyBean, weather.getWeatherId()));
                        QWeather.getWeather7D(MyApplication.getContext(), weatherId, new QWeather.OnResultWeatherDailyListener() {
                            @Override
                            public void onError(Throwable throwable) {
                                Log.w(TAG, "onError: " + throwable.getMessage());
                            }

                            @Override
                            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                                Log.d(TAG, "onSuccess: weatherDaily");
                                weather.setWeatherDaily(new WeatherDaily(weatherDailyBean, weather.getWeatherId()));
                                update(weather);
                            }
                        });
                    }
                });
            }
        });
    }

    public void insertWeatherFromInternet(String weatherId) {
        var weather = new Weather();
        weather.setWeatherId(weatherId);
        final String wId;
        if (weatherId.equals("auto")) {
            weather.setCountryName(ContentUtil.getNowCountryName());
            wId = ContentUtil.getNowLon() + "," + ContentUtil.getNowLat();
        } else {
            wId = weatherId;
            weather.setCountryName(countryRepository.getCountryByWeatherId(weatherId).getCountryName());
        }
        QWeather.getWeatherNow(MyApplication.getContext(), wId, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.w(TAG, "onError: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean) {
                Log.d(TAG, "onSuccess: weatherNow");
                weather.setWeatherNow(new WeatherNow(weatherNowBean));
                QWeather.getWeather24Hourly(MyApplication.getContext(), wId, new QWeather.OnResultWeatherHourlyListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.w(TAG, "onError: " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                        Log.d(TAG, "onSuccess: weatherHourly");
                        weather.setWeatherHourly(new WeatherHourly(weatherHourlyBean, weatherId));
                        QWeather.getWeather7D(MyApplication.getContext(), wId, new QWeather.OnResultWeatherDailyListener() {
                            @Override
                            public void onError(Throwable throwable) {
                                Log.w(TAG, "onError: " + throwable.getMessage());
                            }

                            @Override
                            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                                Log.d(TAG, "onSuccess: weatherDaily");
                                weather.setWeatherDaily(new WeatherDaily(weatherDailyBean, weatherId));
                                insert(weather);
                            }
                        });
                    }
                });
            }
        });
    }
   public void delete(Weather...weathers)
    {
        WeatherDatabase.databaseWriteExecutor.execute(() ->
        {
            weatherDao.delete(weathers);
        });
    }
}

