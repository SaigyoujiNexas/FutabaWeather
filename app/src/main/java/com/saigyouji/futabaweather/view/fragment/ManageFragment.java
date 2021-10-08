package com.saigyouji.futabaweather.view.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.country.Country;
import com.saigyouji.futabaweather.db.weather.Weather;
import com.saigyouji.futabaweather.utils.NetWork;
import com.saigyouji.futabaweather.view.activity.MainActivity;
import com.saigyouji.futabaweather.view.activity.SearchActivity;
import com.saigyouji.futabaweather.view.adapter.CountryManageAdapter;
import com.saigyouji.futabaweather.view.adapter.CountrySearchAdapter;
import com.saigyouji.futabaweather.viewModel.CountryViewModel;
import com.saigyouji.futabaweather.viewModel.WeatherViewModel;

import java.util.List;

public class ManageFragment extends Fragment {
    private RecyclerView recyclerView;
    private CountryManageAdapter adapter;
    private Button addButton;
    private SearchActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.country_manage_frag, container, false);
        activity = (SearchActivity) getActivity();
        recyclerView = v.findViewById(R.id.rv_country_manager);
        addButton = v.findViewById(R.id.b_add_country);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        var adapter = new CountryManageAdapter(new CountryManageAdapter.DiffCallback(), activity.weatherViewModel);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        activity.weatherViewModel.getAllWeathers().observe(activity, weathers ->
        {
            adapter.submitList(weathers);
        });
        addButton.setOnClickListener(v-> {
            activity.runOnUiThread(() -> {
                if(NetWork.checkNetWorkIsConnected())
                activity.switchFragment(new SearchFragment());
                else
                    activity.runOnUiThread(() -> {
                        Toast.makeText(activity, "No netwoke is connected!", Toast.LENGTH_LONG).show();
                    });
            });
        });
    }
}