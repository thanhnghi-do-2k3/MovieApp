package com.example.myapplication.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SeatAdapter;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.Seat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SeatBookingScreen extends AppCompatActivity {
    private FloatingActionButton fab;
    private ImageView backBtn;
    private RecyclerView rvSeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_booking_screen);
        InitView();
        List<Seat> items = DataManager.getInstance(this).seats;

        SeatAdapter adapter = new SeatAdapter(this, items);
        rvSeat.setLayoutManager(new GridLayoutManager(this, 8));
        rvSeat.setAdapter(adapter);

        backBtn.setOnClickListener(v -> {
            finish();
        });
    }

    void InitView() {
        fab = findViewById(R.id.fab);
        backBtn = findViewById(R.id.back_button);
        rvSeat = findViewById(R.id.seat_recycler_view);
    }
}