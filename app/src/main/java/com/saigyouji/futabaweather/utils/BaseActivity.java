package com.saigyouji.futabaweather.utils;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity
{
    private static List<Activity> applicationList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        HeConfig.init(ContentUtil.getPublicId(), ContentUtil.getApkKey());
        HeConfig.switchToDevService();
        ActivityCollector.addActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy()
    {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }
}
