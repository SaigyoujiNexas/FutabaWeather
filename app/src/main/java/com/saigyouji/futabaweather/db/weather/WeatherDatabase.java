package com.saigyouji.futabaweather.db.weather;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Weather.class},version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class WeatherDatabase extends RoomDatabase
{
    public abstract WeatherDao weatherDao();
    private static final int NUM_OF_THREADS = 10;
    private volatile static WeatherDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUM_OF_THREADS);
    public static WeatherDatabase getWeatherDatabase(final Context context)
    {
        if(INSTANCE  == null)
        {
            synchronized (WeatherDatabase.class)
            {
                if(INSTANCE  == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, "weather_table")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

}



