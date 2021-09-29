package com.saigyouji.futabaweather.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;

import com.saigyouji.futabaweather.R;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


    }
    private void initView()
    {
        searchView = findViewById(R.id.sv_search);
    //    searchView.setS
    }
}