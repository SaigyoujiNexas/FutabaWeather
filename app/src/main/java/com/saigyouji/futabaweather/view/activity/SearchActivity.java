package com.saigyouji.futabaweather.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.country.Country;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.utils.BaseActivity;
import com.saigyouji.futabaweather.view.adapter.CountryManageAdapter;
import com.saigyouji.futabaweather.view.adapter.CountrySearchAdapter;
import com.saigyouji.futabaweather.view.fragment.ManageFragment;
import com.saigyouji.futabaweather.viewModel.CountryViewModel;
import com.saigyouji.futabaweather.viewModel.WeatherViewModel;

import java.util.List;

public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";
    public WeatherViewModel weatherViewModel;
    public CountryViewModel countryViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        setContentView(R.layout.activity_search);
        countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);

    }
    public void switchFragment(Fragment fragment)
    {
        var manager = getSupportFragmentManager();
        var transaction = manager.beginTransaction();
        transaction.replace(R.id.manage_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}