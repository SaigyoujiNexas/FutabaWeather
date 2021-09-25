package com.saigyouji.futabaweather.service;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.saigyouji.futabaweather.repository.WeatherRepository;
import com.saigyouji.futabaweather.utils.BaseActivity;
import com.saigyouji.futabaweather.utils.ContentUtil;
import com.saigyouji.futabaweather.utils.MyApplication;
import com.saigyouji.futabaweather.view.activity.MainActivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class LocationService extends Service
{
    private static final String TAG = "LocationService";
    public WeatherRepository weatherRepository;
    public AMapLocationClient mapLocationClient = null;
    public AMapLocationListener locationListener = aMapLocation -> {
        if(aMapLocation.getErrorCode() == 0)
        {
            ContentUtil.setNowLat((float)aMapLocation.getLatitude());
            ContentUtil.setNowLon((float)aMapLocation.getLongitude());
            ContentUtil.setNowCountryName(aMapLocation.getDistrict());
            Log.d(TAG, "Now the location is: " + aMapLocation.getDistrict());
   //         weatherRepository.updateWeathers();
            startActivity(new Intent(this, MainActivity.class));


        }
        else
        {
            String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            Log.e(TAG, errText);
        }
    };
    public LocationService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: location Service created");
        weatherRepository = new WeatherRepository(getApplication());
        mapLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setInterval(10000);
        option.setHttpTimeOut(20000);
        option.setOnceLocation(true);
        mapLocationClient.setLocationListener(locationListener);
        mapLocationClient.setLocationOption(option);
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        mapLocationClient.stopLocation();
        mapLocationClient.startLocation();
  //      weatherRepository.updateWeathers();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}