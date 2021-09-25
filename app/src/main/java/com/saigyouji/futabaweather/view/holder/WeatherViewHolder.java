package com.saigyouji.futabaweather.view.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.weather.weatherHourly.WeatherHourly;
import com.saigyouji.futabaweather.view.adapter.WeatherHourlyAdapter;

public class WeatherViewHolder extends RecyclerView.ViewHolder
{
    private TextView weatherNowTemp;
    private TextView weatherNowText;
    private RecyclerView recyclerViewHourly;
    private WeatherHourlyAdapter adapter;

    public WeatherViewHolder(@NonNull View itemView)
    {
        super(itemView);
        weatherNowTemp = itemView.findViewById(R.id.tv_weather_now_temperature);
        weatherNowText = itemView.findViewById(R.id.tv_weather_now_weather);
        recyclerViewHourly = itemView.findViewById(R.id.rv_weather_hourly);
        adapter = new WeatherHourlyAdapter(new WeatherHourlyAdapter.WeatherHourlyDiff());
        recyclerViewHourly.setAdapter(adapter);
        var manager = new LinearLayoutManager(itemView.getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewHourly.setLayoutManager(manager);


    }
    public static WeatherViewHolder create(ViewGroup parent)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_fragment, parent, false);
        return new WeatherViewHolder(v);
    }
    public void bind (String nowTemp, String nowText, WeatherHourly weatherHourly)
    {

        weatherNowText .setText(nowText);
        weatherNowTemp.setText(nowTemp);
        adapter.submitList(weatherHourly.getWeatherHourlyContentList());
    }
}
