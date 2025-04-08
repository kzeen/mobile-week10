package com.example.mobileweek10;

import android.os.Bundle;
import android.util.Log;

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

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RequestQueue queue = Volley.newRequestQueue(this);

        // For address, we cannot use localhost because we are accessing from emulator,
        // So we don't have access to server and php, and have to access local host
        //through absolute address
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, "http://10.31.200.92/back.php",
                        data -> process(data),
                        error -> handle_error(error));

        queue.add(stringRequest);
    }

    private void process(String data) {
        try {
            JSONObject json = new JSONObject(data);
            Log.d("VOLLEY", json.getJSONObject("student").getString("firstName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handle_error(VolleyError error) {
        Log.d("VOLLEY", error.getMessage());
    }
}