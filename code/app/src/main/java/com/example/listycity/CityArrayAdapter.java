package com.example.listycity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter<City> {

    public CityArrayAdapter(@NonNull Context context, @NonNull ArrayList<City> cities) {
        // We pass 0 as layout because we inflate our own view in getView()
        super(context, 0, cities);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = (convertView == null)
                ? LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false)
                : convertView;

        City city = getItem(position);
        if (city != null) {
            TextView cityName = view.findViewById(R.id.city_text);
            TextView provinceName = view.findViewById(R.id.province_text);
            cityName.setText(city.getName());
            provinceName.setText(city.getProvince());
        }
        return view;
    }
}


