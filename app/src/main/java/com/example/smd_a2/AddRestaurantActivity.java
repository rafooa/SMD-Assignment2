package com.example.smd_a2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class AddRestaurantActivity extends AppCompatActivity {
    private EditText nameEditText, descriptionEditText, phoneNumberEditText, locationEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_resturant);

        nameEditText = findViewById(R.id.edit_text_name);
        descriptionEditText = findViewById(R.id.edit_text_description);
        phoneNumberEditText = findViewById(R.id.edit_text_phone_number);
        locationEditText = findViewById(R.id.edit_text_location);
        Button addButton = findViewById(R.id.button_add);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                String phoneNumber = phoneNumberEditText.getText().toString().trim();
                String location = locationEditText.getText().toString().trim();

                if (!name.isEmpty() && !description.isEmpty() && !phoneNumber.isEmpty() && !location.isEmpty()) {
                    addRestaurant(name, description, phoneNumber, location);
                } else {
                    Toast.makeText(AddRestaurantActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addRestaurant(String name, String description, String phoneNumber, String location) {
        // Construct a JSON string to store restaurant details
        JSONObject restaurantJson = new JSONObject();
        try {
            restaurantJson.put("name", name);
            restaurantJson.put("description", description);
            restaurantJson.put("phoneNumber", phoneNumber); // Use "phoneNumber" consistently
            restaurantJson.put("location", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Get existing restaurant list from SharedPreferences
        Set<String> restaurants = sharedPreferences.getStringSet("restaurants", new HashSet<>());

        // Add new restaurant JSON string to the set
        restaurants.add(restaurantJson.toString());

        // Update the restaurant list in SharedPreferences
        sharedPreferences.edit().putStringSet("restaurants", restaurants).apply();

        // Redirect to MainActivity
        Intent intent = new Intent(AddRestaurantActivity.this, MainActivity.class);
        startActivity(intent);
        // Finish the current activity
        finish();
    }
}
