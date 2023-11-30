package com.example.myapplication.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Seat;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder>  {
    private Context context;
    private List<Seat> items;
    public SeatAdapter(Context context, List<Seat> items) {
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View itemView = inflater.inflate(R.layout.seat_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatAdapter.ViewHolder holder, int position) {
        Seat item = items.get(position);
        if (item.getStatus()) {
            holder.imgSeat.setImageResource(R.drawable.seat);
            holder.imgSeat.setOnClickListener(v -> {
                v.setSelected(!v.isSelected());
            });
        } else {
            holder.imgSeat.setImageResource(R.drawable.booked_seat);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSeat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSeat = itemView.findViewById(R.id.seat_img);
        }
    }
}
