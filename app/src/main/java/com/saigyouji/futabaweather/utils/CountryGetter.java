package com.saigyouji.futabaweather.utils;

import android.util.Log;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.country.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CountryGetter
{
    private static final String TAG = "CountryGetter";
    public static List<Country> getAllCountries()
    {
        List<Country> list = new ArrayList<>();
        var steam = MyApplication.getContext().getResources().openRawResource(R.raw.list);
        var in = new Scanner(steam);
        in.nextLine();
        in.nextLine();
        while(in.hasNextLine())
        {
            String[] get = in.nextLine().split(",");
            String weatherId = get[0];
            String countryName = get[2];
            String provinceName = get[7];
            String cityName = get[9];
            var ins = new Country(weatherId, provinceName, cityName, countryName);
            Log.d(TAG, "getAllCountries: " + weatherId + "province IS" + provinceName + "city is " + cityName + "country is " + countryName);
            list.add(ins);
        }
        return list;
    }
}
