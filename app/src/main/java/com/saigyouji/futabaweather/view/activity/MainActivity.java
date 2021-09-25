package com.saigyouji.futabaweather.view.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.utils.BaseActivity;
import com.saigyouji.futabaweather.utils.ContentUtil;
import com.saigyouji.futabaweather.utils.SpUtils;
import com.saigyouji.futabaweather.view.adapter.MainPagerAdapter;
import com.saigyouji.futabaweather.view.adapter.WeatherDailyAdapter;
import com.saigyouji.futabaweather.view.adapter.WeatherHourlyAdapter;
import com.saigyouji.futabaweather.viewModel.WeatherViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView weatherHourlyRecyclerView;
    private List<WeatherHourlyBean.HourlyBean> hourlyBeanList;
    private ViewPager viewPager;
    private TextView countryName;
    private List<View> viewList;
   // private LiveData<List<Weather>> weatherLiveData;
    private MainPagerAdapter pagerAdapter;

    private WeatherViewModel weatherViewModel;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.vp_weather);
        viewList = new ArrayList<>();
        pagerAdapter = new MainPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);
        countryName = findViewById(R.id.tv_country_text);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
  //      weatherLiveData = weatherViewModel.getAllWeathers();
//        if (SpUtils.getBoolean(this, "auto", true) == true) {
//            weatherViewModel.insertWeatherFromInternet("auto");
 //       }
        weatherViewModel.updateAllWeathers(weatherViewModel.getAllWeathersByList());
        //set the status bar is transparent status bar
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);

        initWeatherView();
    }

    private void initWeatherView()
    {
        weatherViewModel.getAllWeathers().observe(this, weathers -> {
       //     weatherViewModel.updateAllWeathers(weathers);
            viewList.clear();
            var inflater = getLayoutInflater();
            Log.d(TAG, "initWeatherView: observer");
            countryName.setText(weathers.get(0).getCountryName());
            for (Weather w :
                    weathers) {

                View v = inflater.inflate(R.layout.weather_fragment, null);
                TextView weatherNowText = v.findViewById(R.id.tv_weather_now_weather);
                TextView weatherNowTemp = v.findViewById(R.id.tv_weather_now_temperature);
                RecyclerView weatherHourlyRecyclerView = v.findViewById(R.id.rv_weather_hourly);
                RecyclerView weatherDailyRecyclerView = v.findViewById(R.id.rv_weather_daily);

                var weatherHourly = w.getWeatherHourly();
                var weatherDaily = w.getWeatherDaily();

                var hourlyAdapter = new WeatherHourlyAdapter(new WeatherHourlyAdapter.WeatherHourlyDiff());
                var hourlyManager = new LinearLayoutManager(this);
                var dailyAdapter = new WeatherDailyAdapter(new WeatherDailyAdapter.WeatherDailyDiff());
                var dailyManager = new LinearLayoutManager(this);

                hourlyManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                weatherHourlyRecyclerView.setLayoutManager(hourlyManager);
                weatherHourlyRecyclerView.setAdapter(hourlyAdapter);
                hourlyAdapter.submitList(weatherHourly.getWeatherHourlyContentList());

                weatherDailyRecyclerView.setLayoutManager(dailyManager);
                weatherDailyRecyclerView.setAdapter(dailyAdapter);
                dailyAdapter.submitList(weatherDaily.getWeatherDailyContents());

                weatherNowTemp.setText(w.getWeatherNow().getTemperature());
                weatherNowText.setText(w.getWeatherNow().getText());
                viewList.add(v);
            }
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Weather  weather = weathers.get(position);
                    countryName.setText(weather.getCountryName());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            pagerAdapter.notifyDataSetChanged();
        });

    }

}