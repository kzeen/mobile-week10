package com.example.mobileweek10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileweek10.databinding.ActivityDishesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DishesActivity extends AppCompatActivity {
    private ActivityDishesBinding binding;
    private ArrayList<Dish> dishesArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDishesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });


    }

    public void onResume()
    {
        super.onResume();
        fetch_dishes();
    }

    private void fetch_dishes()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest stringRequest =
                new JsonArrayRequest(Request.Method.GET, "http://192.168.0.104/restaurant/getalldishes.php",
                        null,data -> process(data),
                        error -> handle_error(error));

        queue.add(stringRequest);
    }

    private void process(JSONArray data){
        try {
            JSONObject jsondish;
            dishesArrayList = new ArrayList<>();
            for(int i = 0; i < data.length(); i++) {
                jsondish = data.getJSONObject(i);
                String name = jsondish.getString("name");
                Double price = jsondish.getDouble("price");
                String type = jsondish.getString("type");
                int id = jsondish.getInt("id");
                dishesArrayList.add(new Dish(id,name, price, type));
            }
            MyRecyclerViewAdapter myRecyclerViewAdapter;
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(this, dishesArrayList);
            binding.includedSegment.recyclerView.setAdapter(myRecyclerViewAdapter);
            binding.includedSegment.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        catch (Exception e){

        }
    }
    private void handle_error(VolleyError error)
    {

    }

}