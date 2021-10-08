package com.saigyouji.futabaweather.view.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.repository.WeatherRepository;
import com.saigyouji.futabaweather.view.holder.CountryManageHolder;
import com.saigyouji.futabaweather.viewModel.WeatherViewModel;

public class CountryManageAdapter extends ListAdapter<Weather, CountryManageHolder>
{

    private WeatherViewModel viewModel;
    public CountryManageAdapter(@NonNull DiffUtil.ItemCallback<Weather> diffCallback, WeatherViewModel weatherViewModel)
    {
        super(diffCallback);
        this.viewModel = weatherViewModel;


    }

    @NonNull
    @Override
    public CountryManageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return CountryManageHolder.create(parent, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryManageHolder holder, int position) {
        Weather weather = getItem(position);
        holder.bind(weather);
    }
    public static class DiffCallback extends DiffUtil.ItemCallback<Weather>
    {
        @Override
        public boolean areContentsTheSame(@NonNull Weather oldItem, @NonNull Weather newItem) {
            return oldItem.getWeatherId().equals(newItem.getWeatherId());
        }

        @Override
        public boolean areItemsTheSame(@NonNull Weather oldItem, @NonNull Weather newItem) {
            return oldItem == newItem;
        }
    }
}
