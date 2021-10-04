package com.saigyouji.futabaweather.view.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.service.LocationService;
import com.saigyouji.futabaweather.utils.ActivityCollector;
import com.saigyouji.futabaweather.utils.BaseActivity;
import com.saigyouji.futabaweather.utils.MyApplication;
import com.saigyouji.futabaweather.utils.SpUtils;
import com.saigyouji.futabaweather.service.FirstStartService;
import com.saigyouji.futabaweather.utils.ThreadPool;
import com.saigyouji.futabaweather.viewModel.WeatherViewModel;

public class SplashActivity extends BaseActivity {
    private final int REQUEST_PERMISSION_LOCATION = 10;
    private static final String TAG = "SplashActivity";
    private static WeatherViewModel weatherViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        initPermission();
        Button buttonTest = findViewById(R.id.b_test_button);
        buttonTest.setOnClickListener((v) ->{
            startActivity(new Intent(SplashActivity.this, TestActivity.class));
        });
        Log.d(TAG, "onCreate: this will start initPermission");
    }
    private void onFirstStart()
    {
        Log.d(TAG, "onCreate: First Start");
        var intent = new Intent(this, FirstStartService.class);
        startService(intent);
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            // 没有权限
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PERMISSION_LOCATION);
        } else {
            startService(new Intent(this, LocationService.class));
            startIntent();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        boolean requestSuccess = true;
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if(grantResults.length > 0)
                {
                    for(int i = 0; i < grantResults.length; i++)
                    {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        {
                            Log.d(TAG, "onRequestPermissionsResult: no permission");
                            requestSuccess = false;
                            Toast.makeText(this, "无权限！", Toast.LENGTH_LONG).show();
                            ActivityCollector.finishAll();
                        }
                    }
                    if(requestSuccess == true)
                    {
                        startIntent();
                    }
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void startIntent() {
        if(SpUtils.getBoolean(this, "first_start", true) == true)
            onFirstStart();
        var list = weatherViewModel.getAllWeathersByList();
      // weatherViewModel.updateAllWeathers(list);
        var manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + 5 * 1000;
        var intent = new Intent(this, MainActivity.class);
        var pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}