package com.saigyouji.futabaweather.view.fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.country.Country;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.view.activity.MainActivity;
import com.saigyouji.futabaweather.view.activity.SearchActivity;
import com.saigyouji.futabaweather.view.adapter.CountrySearchAdapter;
import com.saigyouji.futabaweather.viewModel.CountryViewModel;
import com.saigyouji.futabaweather.viewModel.WeatherViewModel;

import java.util.List;

public class SearchFragment extends Fragment {
    private List<Country> list;
    private SearchActivity activity;
    private SearchView searchView;
    private ListView listView;
    private CountrySearchAdapter adapter;

    public static ManageFragment newInstance() {
        return new ManageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_frag, container, false);
        activity = (SearchActivity)getActivity();
        SearchManager manager =(SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);

        searchView =(SearchView) v.findViewById(R.id.sv_search);
        searchView.setSearchableInfo(manager.getSearchableInfo(activity.getComponentName()));
        searchView.setIconified(false);
        listView = v.findViewById(R.id.lv_search);
        list = activity.countryViewModel.getAllCountries();
        adapter = new CountrySearchAdapter(getContext(), R.layout.item_search_result, list);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setVisibility(View.INVISIBLE);
        return v;
    }


    private void searchCountry(String str) {
        list.clear();
        var temp = activity.countryViewModel.getCountriesByName(str);
        list.addAll(temp);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                activity.weatherViewModel.insertWeatherFromInternet(weatherId);
                activity.runOnUiThread(() -> {
                    startActivity(new Intent(activity, MainActivity.class));
                });
            }
        });
    }
}
