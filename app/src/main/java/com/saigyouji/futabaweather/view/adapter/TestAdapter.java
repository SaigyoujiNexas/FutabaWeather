package com.saigyouji.futabaweather.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.db.weather.weatherDaily.WeatherDaily;
import com.saigyouji.futabaweather.db.weather.weatherDaily.WeatherDailyContent;

import java.util.List;

public class TestAdapter extends ListAdapter<Weather, TestAdapter.ViewHolder>
{
    private static final String TAG = "TestAdapter";
    class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView   textView;
        private final TextView  textViewWeather;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textView = itemView.findViewById(R.id.test_tv);
            textViewWeather = itemView.findViewById(R.id.test_tv_weather);
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getTextViewWeather() {
            return textViewWeather;
        }
    }

    public TestAdapter(@NonNull DiffUtil.ItemCallback<Weather> diffCallback) {
        super(diffCallback);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextViewWeather().setText(getItem(position).getWeatherId());
        WeatherDaily weatherDaily = getItem(position).getWeatherDaily();
        List<WeatherDailyContent>  weatherDailyContents = weatherDaily.getWeatherDailyContents();
        var array =weatherDailyContents;
        holder.getTextView().setText(array.get(0).getTextDay());
    }

    public static class TestDiff extends DiffUtil.ItemCallback<Weather>
    {
        @Override
        public boolean areContentsTheSame(@NonNull Weather oldItem, @NonNull Weather newItem) {
            return oldItem.getWeatherId().equals(newItem.getWeatherId());
        }

        @Override
        public boolean areItemsTheSame(@NonNull Weather oldItem, @NonNull Weather newItem) {
            return oldItem ==  newItem;
        }
    }
}
