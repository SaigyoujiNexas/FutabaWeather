package com.saigyouji.futabaweather.db.country;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Country.class}, version = 1, exportSchema = false)
public  abstract class CountryDatabase extends RoomDatabase
{
    public abstract CountryDao countryDao();
    private static volatile CountryDatabase INSTANCE;
    private static final int THREAD_NUMBER = 20;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(THREAD_NUMBER);

    public static CountryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CountryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            , CountryDatabase.class, "country_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
