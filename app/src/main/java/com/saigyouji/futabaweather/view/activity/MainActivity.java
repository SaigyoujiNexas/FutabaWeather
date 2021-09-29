package com.saigyouji.futabaweather.view.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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

    private ViewPager2 viewPager;
    private TextView countryName;
    private List<Weather> viewList;
    private MainPagerAdapter pagerAdapter;
    private WeatherViewModel weatherViewModel;
    private static final String TAG = "MainActivity";
    private ImageView add_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        //set the status bar is transparent status bar
        setTransParentStatusBar();
        initWeatherView();
    }
    private void setTransParentStatusBar()
    {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
    private void initView()
    {
        viewPager = findViewById(R.id.vp_weather);
        viewList = new ArrayList<>();
        pagerAdapter = new MainPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);
        countryName = findViewById(R.id.tv_country_text);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.updateAllWeathers(weatherViewModel.getAllWeathersByList());
        add_image = findViewById(R.id.iv_add_city);
        initButton();
    }
    private void initButton()
    {
        add_image.setOnClickListener(v -> {
            var intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });
    }
    private void initWeatherView()
    {
        weatherViewModel.getAllWeathers().observe(this, weathers -> {
       //     weatherViewModel.updateAllWeathers(weathers);
            viewList.clear();
            var inflater = getLayoutInflater();
            Log.d(TAG, "initWeatherView: observer");
            if(weathers.size() <= 0)
                countryName.setText("null");
            else
                countryName.setText(weathers.get(0).getCountryName());
            for (Weather w :
                    weathers) {
                viewList.add(w);
            }
            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                @Override
                public void onPageSelected(int position) {
                    Weather  weather = weathers.get(position);
                    countryName.setText(weather.getCountryName());
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });
            pagerAdapter.notifyDataSetChanged();
        });

    }

}