package com.saigyouji.futabaweather.view.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.weather.weatherDaily.WeatherDaily;

public class WeatherDailyHolder extends RecyclerView.ViewHolder
{
    private TextView weatherDailyTime;
    private ImageView weatherDailyIcon;
    private TextView weatherDailyMaxTemp;
    private TextView weatherDailyMinTemp;

    public WeatherDailyHolder(@NonNull View itemView)
    {
        super(itemView);
        weatherDailyTime = itemView.findViewById(R.id.tv_weather_daily_time);
        weatherDailyIcon = itemView.findViewById(R.id.iv_weather_daily_icon);
        weatherDailyMaxTemp = itemView.findViewById(R.id.tv_weather_daily_max_temp);
        weatherDailyMinTemp = itemView.findViewById(R.id.tv_weather_daily_min_temp);
    }
    public static WeatherDailyHolder create(@NonNull ViewGroup parent)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_weather_daily, parent, false);
        return new WeatherDailyHolder(v);
    }
    public void bind(String time, int iconId, String maxTemp, String minTemp)
    {
        weatherDailyTime.setText(time);
        weatherDailyIcon.setImageResource(iconId);
        weatherDailyMinTemp.setText(minTemp+ "°");
        weatherDailyMaxTemp.setText(maxTemp+ "°");
    }
}
