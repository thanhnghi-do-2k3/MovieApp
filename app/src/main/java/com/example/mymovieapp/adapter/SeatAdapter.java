package com.example.mymovieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovieapp.R;
import com.example.mymovieapp.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {
    private Context context;
    private List<Seat> items;
    private onItemClickListener listener;
    private int number_chosen_seat = 0;
    private List<Integer> bookedSeatId;
    private List<Seat> bookedSeats = new ArrayList<>();

    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public SeatAdapter(Context context, List<Seat> items, onItemClickListener listener, List<Integer> bookedSeatId) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.bookedSeatId = bookedSeatId;
        for (int i = 0; i < bookedSeatId.size(); i++) {
            for (int j = 0; j < items.size(); j++) {
                if (bookedSeatId.get(i) == items.get(j).id) {
                    bookedSeats.add(items.get(j));
                }
            }
        }
        number_chosen_seat = bookedSeatId.size();
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
        if (bookedSeatId.contains(item.id)) {
            holder.imgSeat.setSelected(true);
        }
        if (item.getStatus()) {
            holder.imgSeat.setImageResource(R.drawable.seat);
            holder.imgSeat.setOnClickListener(v -> {
                if (!v.isSelected()) {
                    number_chosen_seat++;
                    bookedSeats.add(item);
                    listener.onClickItem(number_chosen_seat, bookedSeats);
                    v.setSelected(true);
                } else {
                    v.setSelected(false);
                    number_chosen_seat--;
                    bookedSeats.remove(item);
                    listener.onClickItem(number_chosen_seat, bookedSeats);
                }
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

    public interface onItemClickListener {
        void onClickItem(int number_chosen_seat, List<Seat> bookedSeats);
    }
}
