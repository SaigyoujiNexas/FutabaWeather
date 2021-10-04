package com.saigyouji.futabaweather.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.RoomDatabase;

import com.saigyouji.futabaweather.db.country.CountryDao;
import com.saigyouji.futabaweather.db.country.CountryDatabase;
import com.saigyouji.futabaweather.db.weather.WeatherDao;
import com.saigyouji.futabaweather.db.weather.WeatherDatabase;

public class FutabaProvider extends ContentProvider {
    public static final int TABLE_WEATHER_DIR = 0;
    public static final int TABLE_WEATHER_ITEM = 1;
    public static final int TABLE_COUNTRY_DIR = 2;
    public static final int TABLE_COUNTRY_ITEM = 3;
    private static UriMatcher uriMatcher;
    private static final String AUTHORITY = "com.saigyouji.futabaweather.provider";

    private WeatherDatabase weatherDatabase;
    private CountryDatabase countryDatabase;
    private WeatherDao weatherDao;
    private CountryDao countryDao;

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "weather", TABLE_WEATHER_DIR);
        uriMatcher.addURI(AUTHORITY, "weather/#", TABLE_WEATHER_ITEM);
        uriMatcher.addURI(AUTHORITY, "country", TABLE_COUNTRY_DIR);
        uriMatcher.addURI(AUTHORITY, "country/#", TABLE_COUNTRY_ITEM);
    }
    public FutabaProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        weatherDatabase = WeatherDatabase.getWeatherDatabase(getContext());
        countryDatabase = CountryDatabase.getDatabase(getContext());
        weatherDao = weatherDatabase.weatherDao();
        countryDao = countryDatabase.countryDao();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri))
        {
            case TABLE_COUNTRY_ITEM:
                String countryName = uri.getPathSegments().get(1);
                cursor = (Cursor) countryDao.searchCountriesByCountryName(countryName);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}