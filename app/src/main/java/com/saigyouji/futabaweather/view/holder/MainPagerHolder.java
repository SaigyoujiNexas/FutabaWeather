package com.saigyouji.futabaweather.view.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.view.adapter.WeatherDailyAdapter;
import com.saigyouji.futabaweather.view.adapter.WeatherHourlyAdapter;

public class MainPagerHolder extends RecyclerView.ViewHolder
{
    private View view;
    private TextView weatherNowText;
    private TextView weatherNowTemp;
    private RecyclerView weatherHourlyRecyclerView;
    private RecyclerView weatherDailyRecyclerView;

    public MainPagerHolder(@NonNull View itemView)
    {
        super(itemView);
        view = itemView;
        weatherNowText = itemView.findViewById(R.id.tv_weather_now_weather);
        weatherNowTemp = itemView.findViewById(R.id.tv_weather_now_temperature);
        weatherHourlyRecyclerView = itemView.findViewById(R.id.rv_weather_hourly);
        weatherDailyRecyclerView = itemView.findViewById(R.id.rv_weather_daily);
    }

    public static MainPagerHolder create(ViewGroup parent)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_fragment, parent, false);
        return new MainPagerHolder(v);
    }
    public void bind(Weather w)
    {
        var weatherHourly = w.getWeatherHourly();
        var weatherDaily = w.getWeatherDaily();

        var hourlyAdapter = new WeatherHourlyAdapter(new WeatherHourlyAdapter.WeatherHourlyDiff());
        var hourlyManager = new LinearLayoutManager(view.getContext());
        var dailyAdapter = new WeatherDailyAdapter(new WeatherDailyAdapter.WeatherDailyDiff());
        var dailyManager = new LinearLayoutManager(view.getContext());

        hourlyManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        weatherHourlyRecyclerView.setLayoutManager(hourlyManager);
        weatherHourlyRecyclerView.setAdapter(hourlyAdapter);
        hourlyAdapter.submitList(weatherHourly.getWeatherHourlyContentList());

        weatherDailyRecyclerView.setLayoutManager(dailyManager);
        weatherDailyRecyclerView.setAdapter(dailyAdapter);
        dailyAdapter.submitList(weatherDaily.getWeatherDailyContents());

        weatherNowTemp.setText(w.getWeatherNow().getTemperature());
        weatherNowText.setText(w.getWeatherNow().getText());
    }

}
