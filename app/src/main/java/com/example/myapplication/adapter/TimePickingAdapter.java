package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class TimePickingAdapter extends RecyclerView.Adapter<TimePickingAdapter.ViewHolder> {
    private Context context;
    private String[] times = new String[] {"10:00","12:00","14:00","16:00","18:00","20:00","22:00"};
    public TimePickingAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.time_pick_items,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.time.setText(times[position]);
        holder.time.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
        });
    }

    @Override
    public int getItemCount() {
        return times.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_picker);
        }
    }
}
