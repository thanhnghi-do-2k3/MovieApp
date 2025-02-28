package com.example.mymovieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovieapp.R;
import com.example.mymovieapp.Screen.SeatBookingScreen;
import com.example.mymovieapp.models.History;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HistoryCardAdapter extends RecyclerView.Adapter<HistoryCardAdapter.HistoryCardViewHolder> {
    public HistoryCardAdapter(List<History> items, Context context) {
        this.items = items;
        this.context = context;
    }

    private List<History> items;
    private Context context;

    @NonNull
    @Override
    public HistoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card, parent, false);
        return new HistoryCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryCardViewHolder holder, int position) {
        History item = items.get(position);
        holder.movieCinema.setText("Cinema: " + item.getMovieCinema());
        holder.movieDate.setText("Date: " + item.getMovieDate());
        holder.movieName.setText("Movie's Name: " + item.getMovieName());
        holder.moviePrice.setText("Total: " + item.getMoviePrice());
        holder.movieSeat.setText("Number of Seat Booked: " + item.getMovieSeat());
        holder.movieTime.setText("Time: " + item.getMovieTime());

        if (item.getStatus()) {
            holder.status.setImageResource(R.drawable.done_icon);
        } else {
            holder.status.setImageResource(R.drawable.in_progress);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, SeatBookingScreen.class);
                intent.putExtra("time", item.getMovieTime());
                intent.putExtra("date", item.getMovieDate());
                intent.putExtra("cinema", item.getMovieCinema());
                intent.putExtra("poster", item.movie_poster_path);
                intent.putExtra("name", item.getMovieName());
                intent.putExtra("historyId", item.getId());
                List<Integer> seat = new ArrayList<>();
                Log.d("DCMM size", item.seats.size() + "");

                for (int i = 0; i < item.seats.size(); i++) {
                    Log.d("DCMM ID", item.seats.get(i).id + "");
                    seat.add(item.seats.get(i).id);
                }
                intent.putIntegerArrayListExtra("bookedSeatId", (ArrayList<Integer>) seat);
                context.startActivity(intent);
            });
        }

        holder.deleteBtn.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Booking_History").document(item.getId()).delete();
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HistoryCardViewHolder extends RecyclerView.ViewHolder {
        private TextView movieName;
        private TextView movieDate;
        private TextView movieTime;
        private TextView movieCinema;
        private TextView movieSeat;
        private TextView moviePrice;
        private ImageView status;
        private Button deleteBtn;

        public HistoryCardViewHolder(View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_title_1);
            movieDate = itemView.findViewById(R.id.movie_date_1);
            movieTime = itemView.findViewById(R.id.movie_time_1);
            movieCinema = itemView.findViewById(R.id.movie_cinema_1);
            movieSeat = itemView.findViewById(R.id.movie_seat_1);
            moviePrice = itemView.findViewById(R.id.movie_price_1);
            status = itemView.findViewById(R.id.status_icon);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }
}
