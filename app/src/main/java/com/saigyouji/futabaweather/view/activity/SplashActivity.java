package com.saigyouji.futabaweather.view.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.service.LocationService;
import com.saigyouji.futabaweather.utils.BaseActivity;
import com.saigyouji.futabaweather.utils.SpUtils;
import com.saigyouji.futabaweather.service.FirstStartService;
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
        initPermission();

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        Button buttonTest = findViewById(R.id.b_test_button);
        buttonTest.setOnClickListener((v) ->{
            startActivity(new Intent(SplashActivity.this, TestActivity.class));
        });
        Log.d(TAG, "onCreate: this will start initPermission");
        if(SpUtils.getBoolean(this, "first_start", true) == true)
            onFirstStart();

      //  weatherViewModel.updateAllWeathers();
  //      startIntent();
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
  //          startIntent();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
            startService(new Intent(this, LocationService.class));
     //           startIntent();
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startIntent() {

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}