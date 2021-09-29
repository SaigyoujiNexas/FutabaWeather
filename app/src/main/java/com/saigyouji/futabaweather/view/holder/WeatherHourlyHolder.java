package com.saigyouji.futabaweather.view.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saigyouji.futabaweather.R;

public class WeatherHourlyHolder extends RecyclerView.ViewHolder
{
    private TextView weatherHourlyTemp;
    private ImageView weatherHourlyIcon;
    private TextView weatherHourlyText;
    private TextView weatherHourlyTime;
    public WeatherHourlyHolder(@NonNull View itemView) {
        super(itemView);
        weatherHourlyIcon = itemView.findViewById(R.id.iv_weather_hourly_icon);
        weatherHourlyTemp = itemView.findViewById(R.id.tv_weather_hourly_temperature);
        weatherHourlyText = itemView.findViewById(R.id.tv_weather_hourly_weather);
        weatherHourlyTime = itemView.findViewById(R.id.tv_weather_hourly_time);
    }
    public static WeatherHourlyHolder create(ViewGroup parent)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_weather_hourly, parent, false);
        return new WeatherHourlyHolder(v);
    }
    public void bind(String hourlyTemp, int iconId, String hourlyText, String hourlyTime)
    {
        if(hourlyTemp == null)
            hourlyTemp = "null";
        if(hourlyText == null)
            hourlyText = "null";
        if(hourlyTime == null)
            hourlyTime = "null";
        weatherHourlyTime.setText(hourlyTime.substring(11, 16));
        weatherHourlyText.setText(hourlyText);
        weatherHourlyIcon.setImageResource(iconId);
        weatherHourlyTemp.setText(hourlyTemp + "°");
    }
}
