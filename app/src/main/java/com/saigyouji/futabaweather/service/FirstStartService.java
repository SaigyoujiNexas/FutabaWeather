package com.saigyouji.futabaweather.service;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.repository.WeatherRepository;
import com.saigyouji.futabaweather.service.listener.CountryGetListener;
import com.saigyouji.futabaweather.service.task.CountryGetTask;
import com.saigyouji.futabaweather.utils.SpUtils;

public class FirstStartService extends Service
{
    private static final String TAG = "FirstStartService";
    private CountryGetTask countryGetTask;
    private WeatherRepository weatherRepository;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "onStartCommand: ");
   //     ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
 //       progressDialog.show();
    //    weatherRepository.insertWeatherFromInternet("auto");
        Weather weather = new Weather();
        weather.setWeatherId("auto");
        weatherRepository.insert(weather);
        Weather weather2 = new Weather();
        weather2.setWeatherId("101010100");
        weatherRepository.insert(weather2);
        countryGetTask = new CountryGetTask(listener);
        countryGetTask.startGet();
        SpUtils.putBoolean(getApplicationContext(), "first_start", false);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {

        weatherRepository = new WeatherRepository(getApplication());
        super.onCreate();
    }

    private CountryGetListener listener = new CountryGetListener() {
        @Override
        public void onComplete() {
        }
    };

    public FirstStartService()
    {}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
