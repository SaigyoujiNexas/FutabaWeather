package com.saigyouji.futabaweather.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.weather.WeatherDatabase;
import com.saigyouji.futabaweather.view.adapter.TestAdapter;
import com.saigyouji.futabaweather.viewModel.WeatherViewModel;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        RecyclerView recyclerView = findViewById(R.id.rv_test);
        var database =  WeatherDatabase.getWeatherDatabase(this);
        TestAdapter testAdapter = new TestAdapter(new TestAdapter.TestDiff());
        recyclerView.setAdapter(testAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        var viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        var test = viewModel.getAllWeathers();
        test.observe(this, weatherNow ->
        {
            testAdapter.submitList(weatherNow);
        });
    }
}