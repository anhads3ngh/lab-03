package com.example.listycity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Seed data
        dataList = new ArrayList<>();
        dataList.add(new City("Edmonton", "AB"));
        dataList.add(new City("Vancouver", "BC"));
        dataList.add(new City("Toronto", "ON"));

        // ListView + Adapter
        ListView cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // FAB -> open ADD dialog
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v ->
                AddCityFragment.newInstance().show(getSupportFragmentManager(), "add_city_dialog"));

        // TAP -> open EDIT dialog (prefilled)
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City current = dataList.get(position);
            AddCityFragment.newInstanceForEdit(position, current)
                    .show(getSupportFragmentManager(), "edit_city_dialog");
        });
    }

    // ===== Dialog callbacks =====
    @Override
    public void addCity(City city) {
        // Optional: prevent duplicates (case-insensitive on both fields)
        for (City c : dataList) {
            if (c.getName().equalsIgnoreCase(city.getName())
                    && c.getProvince().equalsIgnoreCase(city.getProvince())) {
                Toast.makeText(this, "City already exists.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(int position, City updatedCity) {
        if (position >= 0 && position < dataList.size()) {
            // Optional: prevent duplicates except same index
            for (int i = 0; i < dataList.size(); i++) {
                if (i != position
                        && dataList.get(i).getName().equalsIgnoreCase(updatedCity.getName())
                        && dataList.get(i).getProvince().equalsIgnoreCase(updatedCity.getProvince())) {
                    Toast.makeText(this, "City already exists.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            dataList.set(position, updatedCity);  // replace in list
            cityAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Updated: " + updatedCity, Toast.LENGTH_SHORT).show();
        }
    }
}




