package com.saigyouji.futabaweather.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.country.Country;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.view.adapter.CountrySearchAdapter;
import com.saigyouji.futabaweather.viewModel.CountryViewModel;
import com.saigyouji.futabaweather.viewModel.WeatherViewModel;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    
    private SearchView searchView;
    private CountryViewModel countryViewModel;
    private ListView listView;
    private List<Country> list;
    private WeatherViewModel weatherViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
   //     handleIntent(getIntent());


    }
    private void initView()
    {
        countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        list = countryViewModel.getAllCountries();

        SearchManager manager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =(SearchView) findViewById(R.id.sv_search);
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        listView = findViewById(R.id.lv_search);
        var adapter = new CountrySearchAdapter(this, R.layout.item_search_result, list);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setVisibility(View.INVISIBLE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText))
                {
                    listView.setVisibility(View.VISIBLE);
                    searchCountry(newText);
                    adapter.notifyDataSetChanged();
                }
                else {
                    listView.setVisibility(View.INVISIBLE);
                    listView.clearTextFilter();
                }
                return false;
            }
        });
        setListViewClick();
    }
    private void setListViewClick()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Country country =(Country)listView.getItemAtPosition(position);
                String weatherId = country.getWeatherId();
                String countryName = country.getCountryName();
                Weather weather = new Weather();
                weather.setWeatherId(weatherId);
                weather.setCountryName(countryName);
                weatherViewModel.insert(weather);
                weatherViewModel.updateWeatherFromInternet(weather);
                runOnUiThread(() -> {
                    startActivity(new Intent(SearchActivity.this, MainActivity.class));
                });
                 }
        });
    }





    private void searchCountry(String str)
    {
        list.clear();
        var temp = countryViewModel.getCountriesByName(str);
        list.addAll(temp);

    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent: ");
        setIntent(intent);
   //     handleIntent(intent);
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            Log.d(TAG, "handleIntent: into the search");
            String query = intent.getStringExtra(SearchManager.QUERY);
        }
    }


}