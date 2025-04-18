package com.example.mobileweek10;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileweek10.databinding.ContentAdddishBinding;

public class AddActivity extends AppCompatActivity {

    private ContentAdddishBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ContentAdddishBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btAdd.setOnClickListener(v -> process_add());
    }

    private void process_add() {
        String name_value = binding.edName.getText().toString();
        String price_value = binding.edPrice.getText().toString();
        String type_value = binding.spinner.getSelectedItem().toString();

        RequestQueue queue = Volley.newRequestQueue(this);

        String addURL = "http://192.168.0.104/restaurant/adddish.php?";
        addURL += "name="+name_value;
        addURL += "&price="+price_value;
        addURL += "&type="+type_value;

        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, addURL,
                        data -> show_confirmation(data),
                        error -> handle_error(error));

        queue.add(stringRequest);
    }

    private void show_confirmation(String data) {
        Toast.makeText(this, "Dish added with id = " + data, Toast.LENGTH_SHORT).show();
    }

    private void handle_error(VolleyError error) {

    }
}