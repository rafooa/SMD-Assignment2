package com.example.smd_a2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.widget.Filter;
import android.widget.Filterable;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> implements Filterable {

    private ArrayList<Restaurant> restaurants;
    private ArrayList<Restaurant> filteredList;

    public RestaurantAdapter(ArrayList<Restaurant> list) {
        restaurants = list;
        filteredList = new ArrayList<>(restaurants);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_restaurant_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = filteredList.get(position);
        holder.tvName.setText(restaurant.getName());
        holder.tvDesc.setText(restaurant.getDescription());
        holder.tvLocation.setText(restaurant.getLocation());
        holder.tvContact.setText(restaurant.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Restaurant> filteredResults = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredResults.addAll(restaurants);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Restaurant restaurant : restaurants) {
                        if (restaurant.getName().toLowerCase().contains(filterPattern)) {
                            filteredResults.add(restaurant);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List<Restaurant>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDesc, tvName, tvLocation, tvContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tvRestaurantDesc);
            tvLocation = itemView.findViewById(R.id.tvRestaurantLocation);
            tvName = itemView.findViewById(R.id.tvRestaurantName);
            tvContact = itemView.findViewById(R.id.tvRestaurantContact);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), tvLocation.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
