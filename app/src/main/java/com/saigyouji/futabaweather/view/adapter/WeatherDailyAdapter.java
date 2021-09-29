package com.saigyouji.futabaweather.view.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.weather.weatherDaily.WeatherDailyContent;
import com.saigyouji.futabaweather.utils.PicUtil;
import com.saigyouji.futabaweather.view.holder.WeatherDailyHolder;
import com.saigyouji.futabaweather.view.holder.WeatherViewHolder;

public class WeatherDailyAdapter extends ListAdapter<WeatherDailyContent, WeatherDailyHolder>
{
    public WeatherDailyAdapter(@NonNull DiffUtil.ItemCallback<WeatherDailyContent> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public WeatherDailyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return WeatherDailyHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDailyHolder holder, int position) {
        var item = getItem(position);
        String time = item.getFxDate();
        int icon = PicUtil.getWeatherIcon(item.getIconDay());
        String maxTemp = item.getTempMax();
        String minTemp = item.getTempMin();
        holder.bind(time, icon, maxTemp, minTemp);
    }
    public static class WeatherDailyDiff extends DiffUtil.ItemCallback<WeatherDailyContent>
    {
        @Override
        public boolean areItemsTheSame(@NonNull WeatherDailyContent oldItem, @NonNull WeatherDailyContent newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull WeatherDailyContent oldItem, @NonNull WeatherDailyContent newItem) {
            return oldItem.getWeatherId().equals(newItem.getWeatherId());
        }
    }
}
