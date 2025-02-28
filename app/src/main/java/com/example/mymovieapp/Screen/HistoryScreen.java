package com.example.mymovieapp.Screen;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovieapp.R;
import com.example.mymovieapp.adapter.HistoryCardAdapter;
import com.example.mymovieapp.models.History;
import com.example.mymovieapp.models.Seat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryScreen extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView rvHistory;
    private List<History> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_screen);
        InitView();

        db.collection("Booking_History")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG,
                                        document.getId() + " => " + document.getData());
                                History history = new History();
                                history.setMovieName(document.getString("movieName"));
                                history.setShowDate(document.getString("showDate"));
                                history.setShowTime(document.getString("showTime"));
                                history.setTheaterName(document.getString("theaterName"));
                                history.setTotalSeat(document.getString("totalSeat"));
                                history.setTotalPrice(document.getString("totalPrice"));
                                history.setStatus(document.getBoolean("status"));
                                history.movie_poster_path = document.getString("movie_poster_path");
                                List<Map<String, Object>> temp = (List<Map<String, Object>>) document.get("seats");
                                for (Map<String, Object> seat : temp) {
                                    Seat s = new Seat();
                                    s.id = Integer.parseInt(seat.get("id").toString());
                                    Boolean tempStatus = Boolean.parseBoolean(seat.get("status").toString());
                                    if (tempStatus) {
                                        s.status = "available";
                                    } else {
                                        s.status = "booked";
                                    }
                                    history.seats.add(s);
                                }
                                history.setId(document.getId());

                                items.add(history);
                            }
                            HistoryCardAdapter adapter = new HistoryCardAdapter(items, HistoryScreen.this);
                            rvHistory.setAdapter(adapter);
                            rvHistory.setLayoutManager(new LinearLayoutManager(HistoryScreen.this));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void InitView() {
        db = FirebaseFirestore.getInstance();
        rvHistory = findViewById(R.id.recyclerViewHistory);
    }
}