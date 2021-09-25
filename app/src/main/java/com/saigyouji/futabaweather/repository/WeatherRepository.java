package com.saigyouji.futabaweather.repository;

import android.app.Application;
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
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.db.weather.WeatherDao;
import com.saigyouji.futabaweather.db.weather.WeatherDatabase;
import com.saigyouji.futabaweather.db.weather.WeatherNow;
import com.saigyouji.futabaweather.db.weather.weatherDaily.WeatherDaily;
import com.saigyouji.futabaweather.db.weather.weatherHourly.WeatherHourly;
import com.saigyouji.futabaweather.utils.ContentUtil;
import com.saigyouji.futabaweather.utils.MyApplication;
import com.saigyouji.futabaweather.utils.ThreadPool;

import java.util.List;

public class WeatherRepository
{
    private static final String TAG = "WeatherRepository";
    private  LiveData<List<Weather>> AllWeathers;
    private WeatherDao weatherDao;

    /**
     * handler message number
     */
    private static final int WEATHER_GET_NOW = 1;
    private static final int WEATHER_GET_DAILY = 2;
    private static final int  WEATHER_GET_HOURLY = 3;
    private static final int  WEATHER_GET_COUNTRY  =  4;


    public WeatherRepository(Application application)
    {
        var db = WeatherDatabase.getWeatherDatabase(application);
        weatherDao = db.weatherDao();
        AllWeathers = weatherDao.getAllWeather();
    //    list = weatherDao.getAllWeathersByList();
    }
    public LiveData<List<Weather>> getAllWeathers() {
        return AllWeathers;
    }
    public List<Weather> getAllWeathersByList()
    {
        return weatherDao.getAllWeathersByList();
    }

    public void insert(Weather...weathers)
    {
        WeatherDatabase.databaseWriteExecutor.execute(() -> {
            weatherDao.insert(weathers);
            Log.d(TAG, "insert: " + weathers[0].getWeatherId());
        });
    }
    public void update(Weather... weathers)
    {
        weatherDao.update(weathers);
    }
    public void updateWeathers(List<Weather> weathers) {

            Log.d(TAG, "updateWeathers: weathers get complete" + weathers.size());

            for (int i = 0; i < weathers.size(); i++) {
                Weather w = weathers.get(i);
                Log.d(TAG, "updateWeathers: the item is" + w.getCountryName());
                insertWeatherFromInternet(w.getWeatherId());
            }
            Log.d(TAG, "updateWeathers: update complete");
    }
    public Weather insertWeatherFromInternet(String weatherId)
    {
        var weather = new Weather();
        weather.setWeatherId(weatherId);

        final String wId;
        if(weatherId.equals("auto"))
        {
            wId= ContentUtil.getNowLon() + "," + ContentUtil.getNowLat();
            weather.setCountryName(ContentUtil.getNowCountryName());
        }
        else
        {
            wId = weatherId;
            CountryRepository countryRepository = new CountryRepository(MyApplication.getApplication());
            var country = countryRepository.getCountryByWeatherId(weatherId);
            weather.setCountryName(country.getCountryName());
        }
        final WeatherNowBean[] weatherNowBea = new WeatherNowBean[1];
        final WeatherDailyBean[] weatherDailyBeans = new WeatherDailyBean[1];
        final WeatherHourlyBean[] weatherHourlyBeans = new WeatherHourlyBean[1];

        var handler = new Handler(Looper.myLooper())
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what)
                {
                    case WEATHER_GET_NOW:
                        WeatherNow weatherNow = new WeatherNow(weatherNowBea[0]);
                        Log.d(TAG, "handleMessage: weatherNow" + weatherNow.getText());
                        weather.setWeatherNow(weatherNow);
                        break;
                    case WEATHER_GET_HOURLY:
                        WeatherHourly weatherHourly = new WeatherHourly(weatherHourlyBeans[0], weatherId);
                        Log.d(TAG, "handleMessage: weatherHourly" + weatherHourly.getWeatherHourlyContentList().get(0).getText());
                        weather.setWeatherHourly(weatherHourly);
                        break;
                    case WEATHER_GET_DAILY:
                        WeatherDaily weatherDaily = new WeatherDaily(weatherDailyBeans[0], weatherId);
                        Log.d(TAG, "handleMessage: weatherDaily" + weatherDaily.getWeatherDailyContents().get(0).getTextDay());
                        weather.setWeatherDaily(weatherDaily);
                        insert(weather);
                }
                super.handleMessage(msg);
            }
        };

        QWeather.getWeatherNow(MyApplication.getContext(), wId, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.w(TAG, "onError: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean)
            {
                weatherNowBea[0] = weatherNowBean;
                Message message = new Message();
                message.what = WEATHER_GET_NOW;
                //           message.obj = weatherNowBean;
                handler.sendMessage(message);
                QWeather.getWeather24Hourly(MyApplication.getContext(), wId, new QWeather.OnResultWeatherHourlyListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.w(TAG, "onError: "  + throwable.getMessage() );
                    }

                    @Override
                    public void onSuccess(WeatherHourlyBean weatherHourlyBean)
                    {
                        weatherHourlyBeans[0] = weatherHourlyBean;
                        Message message =   new Message();
                        message.what = WEATHER_GET_HOURLY;
                        handler.sendMessage(message);
                        QWeather.getWeather7D(MyApplication.getContext(), wId, new QWeather.OnResultWeatherDailyListener() {
                            @Override
                            public void onError(Throwable throwable) {
                                Log.w(TAG, "onError: " + throwable.getMessage());
                            }

                            @Override
                            public void onSuccess(WeatherDailyBean weatherDailyBean)
                            {
                                weatherDailyBeans[0] = weatherDailyBean;
                                Message message = new Message();
                                message.what = WEATHER_GET_DAILY;
                                handler.sendMessage(message);
                            }
                        });
                    }
                });
            }
        });
        return weather;
    }


}
