package com.saigyouji.futabaweather.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saigyouji.futabaweather.R;
import com.saigyouji.futabaweather.db.country.Country;
import com.saigyouji.futabaweather.view.holder.CountrySearchHolder;
import com.saigyouji.futabaweather.viewModel.CountryViewModel;

import java.util.List;

public class CountrySearchAdapter extends ArrayAdapter<Country>
{
    private int resourceId;

    public CountrySearchAdapter(@NonNull Context context, int resource, @NonNull List<Country> objects)
    {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Country country = getItem(position);
        View v;
        CountrySearchHolder holder;
        if(convertView == null)
        {
            v = LayoutInflater.from(getContext()).inflate(resourceId,parent, false);
            holder = new CountrySearchHolder();
            holder.setTextView(v.findViewById(R.id.tv_search_result));
            v.setTag(holder);
        }
        else
        {
            v = convertView;
            holder = (CountrySearchHolder) v.getTag();
        }
        holder.getTextView().setText(country.getProvinceName() + "," + country.getCityName()  + "," + country.getCountryName());
        return v;
    }
}
