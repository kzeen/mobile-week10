package com.example.mobileweek10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Dish> itemList;
    public int inflateCounter=0;

    // Constructor
    public MyRecyclerViewAdapter(Context context, List<Dish> itemList) {
        super();
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        inflateCounter++;
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        Dish item = itemList.get(position);
        viewHolder.dname.setText(item.getName());
        viewHolder.dtype.setText(item.getType());
        viewHolder.dprice.setText(item.getPrice()+"");
        viewHolder.delete.setOnClickListener(v -> {
            int pos = viewHolder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                procces_delete(pos);
            }
        });

    }

    private void procces_delete(int position)
    {
        RequestQueue queue = Volley.newRequestQueue(context);

        long id = itemList.get(position).getId();
        String deleteURL = "http://192.168.0.104/restaurant/deletedish.php?id="+id;

        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, deleteURL,
                    data -> process_delete(position, data),
                    error -> handle_error(error));

        queue.add(stringRequest);
    }

    private void process_delete(int position, String data) {
        itemList.remove(position);
        notifyDataSetChanged();
    }

    private void handle_error(VolleyError error) {
        Toast.makeText(context, "Error deleting the dish", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dname;
        ImageView delete;
        TextView dprice;
        TextView dtype;

        public MyViewHolder(View convertView)
        {
            super(convertView);
            dname = convertView.findViewById(R.id.dname);
            delete = convertView.findViewById(R.id.delete);
            dprice = convertView.findViewById(R.id.dprice);
            dtype = convertView.findViewById(R.id.dtype);
        }
    }
}
