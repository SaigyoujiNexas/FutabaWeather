package com.saigyouji.futabaweather.utils;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application
{
    private static Application application = null;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getApplication() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }
}
