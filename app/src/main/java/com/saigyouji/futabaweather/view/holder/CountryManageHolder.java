package com.saigyouji.futabaweather.view.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.repository.WeatherRepository;
import com.saigyouji.futabaweather.utils.PicUtil;
import com.saigyouji.futabaweather.viewModel.WeatherViewModel;

public class CountryManageHolder extends RecyclerView.ViewHolder
{
    private ImageView countryWeatherNow;
    private TextView countryName;
    private TextView countryWeather;
    private TextView countryTemp;
    private ImageView deleteButton;
    private WeatherViewModel viewModel;

    public CountryManageHolder(@NonNull View itemView, WeatherViewModel viewModel) {
        super(itemView);
        this.viewModel = viewModel;
        countryWeatherNow = itemView.findViewById(R.id.iv_country_manage_now_weather);
        countryName = itemView.findViewById(R.id.tv_country_manage_name);
        countryWeather = itemView.findViewById(R.id.tv_country_manage_weather);
        countryTemp = itemView.findViewById(R.id.tv_country_manage_temp);
        deleteButton = itemView.findViewById(R.id.iv_country_manage_delete);
    }
    public static CountryManageHolder create(ViewGroup parent, WeatherViewModel viewModel)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CountryManageHolder(v, viewModel);
    }
    public void bind(Weather weather)
    {
        countryWeatherNow.setImageResource(PicUtil.getWeatherIcon(weather.getWeatherNow().getIcon()));
        countryWeather.setText(weather.getWeatherNow().getText());
       countryTemp.setText(weather.getWeatherNow().getTemperature());

       if(weather.getWeatherId().equals("auto"))
       {
           countryName.setText("自动定位:" + weather.getCountryName());
           deleteButton.setVisibility(View.GONE);
       }
       else {
           countryName.setText(weather.getCountryName());
           deleteButton.setOnClickListener(v -> {
               viewModel.delete(weather);
           });
       }
    }
}
