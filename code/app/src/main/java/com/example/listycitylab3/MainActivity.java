package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements
        AddCityFragment.AddCityDialogListener {
    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;
    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }
    @Override
    public void updateCity(City city, int position) {
        cityAdapter.remove(dataList.get(position));
        cityAdapter.insert(city, position);
        cityAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);

        cityAdapter = new CityArrayAdapter(this, dataList);

        cityList.setAdapter(cityAdapter);

// When a row is clicked, edit the city
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City clicked = cityAdapter.getItem(position);
            AddCityFragment.newInstance(clicked, position)
                    .show(getSupportFragmentManager(), "EditCity");
        });

// FAB still opens an empty dialog for adding new
        findViewById(R.id.button_add_city).setOnClickListener(v -> {
            AddCityFragment.newInstance(null, -1)
                    .show(getSupportFragmentManager(), "AddCity");
        });
    }
}