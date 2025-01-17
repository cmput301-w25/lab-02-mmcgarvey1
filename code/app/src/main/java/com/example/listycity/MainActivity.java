package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int currentCheckedCity = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);
        String[] cities = {
                "Edmonton",
                "Paris",
                "London",
                "Ottawa"
        };

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            currentCheckedCity = position;
        });
    }

    public void addCity() { // listen for click on R.id.add_city, then show R.id.add_city_input and R.id.add_city_confirm
        findViewById(R.id.add_city_input).setVisibility(View.VISIBLE);
        findViewById(R.id.add_city_confirm).setVisibility(View.VISIBLE);
    }

    public void confirmCity() { // listen for click on R.id.add_city_confirm, hide R.id.add_city_input and R.id.add_city_confirm. if a city is entered, add it to the list
        findViewById(R.id.add_city_input).setVisibility(View.GONE);
        findViewById(R.id.add_city_confirm).setVisibility(View.GONE);
        String city = ((EditText) findViewById(R.id.add_city_input)).getText().toString();
        if (!city.isEmpty()) {
            dataList.add(city);
            cityAdapter.notifyDataSetChanged();
        }
    }

    public void deleteCity() { // listen for click on R.id.delete_city, hide R.id.add_city_input and R.id.add_city_confirm. if a city is selected, remove it from the list
        findViewById(R.id.add_city_input).setVisibility(View.GONE);
        findViewById(R.id.add_city_confirm).setVisibility(View.GONE);
        if (currentCheckedCity != ListView.INVALID_POSITION) {
            dataList.remove(currentCheckedCity);
            cityAdapter.notifyDataSetChanged();
            currentCheckedCity = -1;
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_city) {
            addCity();
        } else if (id == R.id.add_city_confirm) {
            confirmCity();
        } else if (id == R.id.delete_city) {
            deleteCity();
        }
    }
}