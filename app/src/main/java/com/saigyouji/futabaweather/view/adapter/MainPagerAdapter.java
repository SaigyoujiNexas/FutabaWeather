package com.saigyouji.futabaweather.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.view.holder.MainPagerHolder;
import com.saigyouji.futabaweather.view.holder.WeatherViewHolder;

import java.util.List;

public class MainPagerAdapter extends RecyclerView.Adapter<MainPagerHolder>
{
    private List<Weather> viewList;

    public MainPagerAdapter(List<Weather> viewList) {
        this.viewList = viewList;
    }

    @NonNull
    @Override
    public MainPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MainPagerHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPagerHolder holder, int position) {
        holder.bind(viewList.get(position));
    }

    @Override
    public int getItemCount() {
        return viewList.size();
    }
}

