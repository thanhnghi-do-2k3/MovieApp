package com.example.mymovieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovieapp.R;

public class TimePickingAdapter extends RecyclerView.Adapter<TimePickingAdapter.ViewHolder> {
    private Context context;
    private boolean isSelected = false;
    private boolean elementSelected = false;
    private onItemClickListener listener;
    private int id;
    private String[] times = new String[]{"10:00", "12:00", "14:00", "16:00", "18:00", "20:00", "22:00"};

    public TimePickingAdapter(Context context, onItemClickListener listener, int id) {
        this.context = context;
        this.listener = listener;
        this.id = id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.time_pick_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.time.setText(times[position]);
    }

    @Override
    public int getItemCount() {
        return times.length;
    }

    public void setSelectedState(boolean isSelected) {
        this.isSelected = isSelected;
        if (isSelected) {
            notifyDataSetChanged();
        }
    }

    public boolean getSelectedState() {
        return isSelected;
    }

    public int getId() {
        return id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_picker);
            time.setOnClickListener(v -> {
                if (!isSelected) {
                    if (!elementSelected) {
                        v.setSelected(true);
                        elementSelected = true;
                        listener.onItemClick(TimePickingAdapter.this, this);
                    } else {
                        if (v.isSelected()) {
                            v.setSelected(false);
                            elementSelected = false;
                            listener.onItemClickRestored(TimePickingAdapter.this);
                        }
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(TimePickingAdapter adapter, ViewHolder holder);

        void onItemClickRestored(TimePickingAdapter adapter);
    }
}
