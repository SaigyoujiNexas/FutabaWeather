package com.saigyouji.futabaweather.view.activity;

import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.utils.BaseActivity;
import com.saigyouji.futabaweather.utils.NetWork;
import com.saigyouji.futabaweather.utils.ThreadPool;
import com.saigyouji.futabaweather.view.ControlPart.MySwipeRefreshLayout;
import com.saigyouji.futabaweather.view.adapter.MainPagerAdapter;
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
    private MySwipeRefreshLayout refreshLayout;
    private String weatherId;
    private Weather currentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        update();
        setTransParentStatusBar();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initWeatherView();
    }

    private void update()
    {
        ThreadPool.addTask(() -> {
            weatherViewModel.updateWeathersFromInternet(weatherViewModel.getAllWeathersByList());
        });
    }


    private void setTransParentStatusBar()
    {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
    private void initView()
    {

        refreshLayout = findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.design_default_color_primary));
        refreshLayout.setOnRefreshListener(() -> {
            if(NetWork.checkNetWorkIsConnected())
            weatherViewModel.updateWeatherFromInternet(currentWeather);
            else
                runOnUiThread(() -> {
                    refreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "No network is connected !", Toast.LENGTH_LONG).show();
                });
        });
        viewPager = findViewById(R.id.vp_weather);
        viewList = new ArrayList<>();
        pagerAdapter = new MainPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);
        countryName = findViewById(R.id.tv_country_text);
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
            refreshLayout.setRefreshing(false);
            viewList.clear();
            if(currentWeather == null)
            currentWeather = weathers.get(0);
            //else is on SwipeRefresh to refresh it.
            Log.d(TAG, "initWeatherView: observer");
            if(weathers.size() <= 0) {
                countryName.setText("null");
            }
            else {
                countryName.setText(currentWeather.getCountryName());
                weatherId = currentWeather.getWeatherId();
            }
            viewList.addAll(weathers);
            for(int i = 0; i < viewList.size(); i++)
            {
                if(viewList.get(i).getWeatherId().equals(currentWeather.getWeatherId()))
                {
                    viewPager.setCurrentItem(i);
                    break;
                }
            }
            pagerAdapter.notifyDataSetChanged();
            Log.d(TAG, "initWeatherView: " + pagerAdapter.getItemCount());
            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                @Override
                public void onPageSelected(int position) {
                    currentWeather = viewList.get(position);
                    countryName.setText(currentWeather.getCountryName());
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initWeatherView();
    }
}