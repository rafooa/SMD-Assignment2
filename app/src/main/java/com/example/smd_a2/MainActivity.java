package com.example.smd_a2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smd_a2.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SearchView searchEditText;
    private RecyclerView rvRestaurants;
    private RestaurantAdapter restaurantAdapter;
    private ArrayList<Restaurant> restaurantList;
    private static final int REQUEST_ADD_RESTAURANT = 1;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        searchEditText = findViewById(R.id.search_edit_text);
        // Initialize RecyclerView and RestaurantAdapter
        initRecyclerView();

        // Initialize FloatingActionButton click listener
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddRestaurantActivity.class), REQUEST_ADD_RESTAURANT);
            }
        });

        searchEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Call filter method of your RecyclerView adapter
                restaurantAdapter.getFilter().filter(newText);
                return true;
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload restaurant list when activity is resumed
        loadRestaurantList();
    }

    private void initRecyclerView() {
        rvRestaurants = binding.rvRestaurants;
        rvRestaurants.setLayoutManager(new LinearLayoutManager(this));
        restaurantList = new ArrayList<>();
        restaurantAdapter = new RestaurantAdapter(restaurantList);
        rvRestaurants.setAdapter(restaurantAdapter);
    }

    private void loadRestaurantList() {
        Set<String> restaurantSet = sharedPreferences.getStringSet("restaurants", new HashSet<>());
        restaurantList.clear();
        for (String jsonRestaurant : restaurantSet) {
            try {
                JSONObject restaurantObject = new JSONObject(jsonRestaurant);
                String name = restaurantObject.getString("name");
                String description = restaurantObject.getString("description");
                String phoneNumber = restaurantObject.getString("phoneNumber");
                String location = restaurantObject.getString("location");
                restaurantList.add(new Restaurant(name, description, phoneNumber, location));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Update the filtered list in the adapter
        restaurantAdapter.getFilter().filter(searchEditText.getQuery().toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_RESTAURANT && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String description = data.getStringExtra("description");
            String phoneNumber = data.getStringExtra("phoneNumber");
            String location = data.getStringExtra("location");
            addRestaurant(name, description, phoneNumber, location);
        }
    }

    private void addRestaurant(String name, String description, String phoneNumber, String location) {
        JSONObject restaurantObject = new JSONObject();
        try {
            restaurantObject.put("name", name);
            restaurantObject.put("description", description);
            restaurantObject.put("phoneNumber", phoneNumber);
            restaurantObject.put("location", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Set<String> restaurantSet = sharedPreferences.getStringSet("restaurants", new HashSet<>());
        restaurantSet.add(restaurantObject.toString());
        sharedPreferences.edit().putStringSet("restaurants", restaurantSet).apply();

        loadRestaurantList(); // Reload restaurant list after adding a new restaurant
    }
}
