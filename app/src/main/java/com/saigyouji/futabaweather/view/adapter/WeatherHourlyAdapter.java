package com.saigyouji.futabaweather.view.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.saigyouji.futabaweather.db.weather.weatherHourly.WeatherHourly;
import com.saigyouji.futabaweather.db.weather.weatherHourly.WeatherHourlyContent;
import com.saigyouji.futabaweather.view.holder.WeatherHourlyHolder;

public class WeatherHourlyAdapter extends ListAdapter<WeatherHourlyContent, WeatherHourlyHolder>
{
    public WeatherHourlyAdapter(@NonNull DiffUtil.ItemCallback<WeatherHourlyContent> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public WeatherHourlyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return WeatherHourlyHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHourlyHolder holder, int position) {
        var item = getItem(position);
        String time =  item.getFxDate();
        int icon =  Integer.parseInt(item.getIcon());
        String temp = item.getTemperature();
        String text = item.getText();
        holder.bind(temp, icon,text, time);
    }
    public static class WeatherHourlyDiff extends DiffUtil.ItemCallback<WeatherHourlyContent>{
        @Override
        public boolean areItemsTheSame(@NonNull WeatherHourlyContent oldItem, @NonNull WeatherHourlyContent newItem) {
            return oldItem ==  newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull WeatherHourlyContent oldItem, @NonNull WeatherHourlyContent newItem) {
            return oldItem.getWeatherId().equals(newItem.getWeatherId());
        }
    }
}
