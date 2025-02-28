package com.example.mymovieapp.Screen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.mymovieapp.R;
import com.example.mymovieapp.adapter.SeatAdapter;
import com.example.mymovieapp.models.DataManager;
import com.example.mymovieapp.models.MovieInfo;
import com.example.mymovieapp.models.Seat;
import com.example.mymovieapp.UIHelper.SystemUiHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatBookingScreen extends AppCompatActivity {
    private FloatingActionButton fab;
    private ImageView backBtn;
    private RecyclerView rvSeat;
    private ImageView movieImg;
    private TextView title;
    private MovieInfo movie;
    private TextView Date;
    private TextView Time;
    private TextView Cinema;
    private TextView numberSeat;
    private TextView totalMoney;
    private String choosenSeat = "x0";
    private String choosenSeatPrice = "$ 0";
    private List<Seat> bookedSeats;
    private FirebaseFirestore db;
    private boolean booked = false;
    private String historyId = "";
    private boolean change = false;
    String time;
    String date;
    String cinema;
    List<Integer> bookedSeatId;
    String poster_path;
    String title_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_booking_screen);

        db = FirebaseFirestore.getInstance();
        time = getIntent().getStringExtra("time");
        date = getIntent().getStringExtra("date");
        cinema = getIntent().getStringExtra("cinema");
        bookedSeatId = getIntent().getIntegerArrayListExtra("bookedSeatId");
        historyId = getIntent().getStringExtra("historyId");

        if (bookedSeatId == null) {
            bookedSeatId = new ArrayList<Integer>();
        }

        InitView();
        List<Seat> items = DataManager.getInstance(this).seats;

        SeatAdapter adapter = new SeatAdapter(this, items, new SeatAdapter.onItemClickListener() {
            @Override
            public void onClickItem(int Seat, List<Seat> bookedSeats) {
                choosenSeat = "x" + String.valueOf(bookedSeats.size());
                choosenSeatPrice = "$ " + String.valueOf(bookedSeats.size() * 5);
                SeatBookingScreen.this.bookedSeats = bookedSeats;
                numberSeat.setText(choosenSeat);
                totalMoney.setText(choosenSeatPrice);
                change = true;
            }
        }, bookedSeatId);
        rvSeat.setLayoutManager(new GridLayoutManager(this, 8));
        rvSeat.setAdapter(adapter);
        this.bookedSeats = adapter.getBookedSeats();

        choosenSeat = "x" + String.valueOf(bookedSeats.size());
        choosenSeatPrice = "$ " + String.valueOf(bookedSeats.size() * 5);

        backBtn.setOnClickListener(v -> {
            if (bookedSeats.size() == 0)
                finish();
            else {
                Intent back_intent = new Intent(this, HomeScreen.class);
                back_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(this, "Booking cancelled! You can continue this session in Booking History", Toast.LENGTH_SHORT).show();
                startActivity(back_intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Log.d("MoviePageScreen", "bundle: " + bundle.toString());
            movie = (MovieInfo) bundle.getSerializable("movie");
        }

        if (movie == null) {
            poster_path = getIntent().getStringExtra("poster");
            title_movie = getIntent().getStringExtra("name");
        } else {
            poster_path = movie.poster_path;
            title_movie = movie.title;
        }

        String url = "https://image.tmdb.org/t/p/w500" + poster_path;
        Glide.with(this)
                .load(url)
                .override(movieImg.getWidth(), Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.no_img_avai)
                .into(movieImg);

        title.setText(title_movie);
        Time.setText(time);
        Date.setText(date);
        Cinema.setText(cinema);
        numberSeat.setText(choosenSeat);
        totalMoney.setText(choosenSeatPrice);

        fab.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.booking_notification_dialog);

            Window window = dialog.getWindow();
            if (window == null) {
                return;
            }

            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowAttributes);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

            ((TextView) dialog.findViewById(R.id.movie_title)).setText("Movie: " + title_movie);
            ((TextView) dialog.findViewById(R.id.movie_date)).setText("Date: " + date);
            ((TextView) dialog.findViewById(R.id.movie_time)).setText("Start time: " + time);
            ((TextView) dialog.findViewById(R.id.movie_cinema)).setText("Cinema: " + cinema);
            ((TextView) dialog.findViewById(R.id.movie_total_seats)).setText("Booked Seat: " + String.valueOf(choosenSeat));
            ((TextView) dialog.findViewById(R.id.movie_total_price)).setText("Total Price: " + String.valueOf(choosenSeatPrice));

            dialog.setCancelable(false);

            ((Button) dialog.findViewById(R.id.cancel_button)).setOnClickListener(v1 -> {
                dialog.dismiss();
                SystemUiHelper.enableImmersiveMode(this);
                Toast.makeText(this, "Booking canceled :(", Toast.LENGTH_SHORT).show();
            });

            ((Button) dialog.findViewById(R.id.book_button)).setOnClickListener(v1 -> {
                Log.d("SeatBookingScreen", "onStop: ");
                booked = true;
                if (bookedSeats.size() == 0) {
                    Toast.makeText(this, "Please choose at least 1 seat", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.dismiss();
                SystemUiHelper.enableImmersiveMode(this);
                Intent new_intent = new Intent(this, HomeScreen.class);
                new_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(this, "Booking successfully! Thank you very much :>", Toast.LENGTH_SHORT).show();

                List<Map<String, Object>> seatToSave = new ArrayList<>();


                for (Seat seat : bookedSeats) {
                    Map<String, Object> seatMap = new HashMap<>();
                    seatMap.put("id", seat.id);
                    if (bookedSeats.contains(seat)) {
                        seatMap.put("status", "booked");
                    } else {
                        seatMap.put("status", seat.status);
                    }
                    seatToSave.add(seatMap);
                }

                Map<String, Object> bookingInfo = new HashMap<>();
                bookingInfo.put("seats", seatToSave);
                bookingInfo.put("movieName", title_movie);
                bookingInfo.put("showTime", time);
                bookingInfo.put("showDate", date);
                bookingInfo.put("theaterName", cinema);
                bookingInfo.put("totalPrice", choosenSeatPrice);
                bookingInfo.put("totalSeat", choosenSeat);
                bookingInfo.put("movie_poster_path", poster_path);
                bookingInfo.put("status", true);

                if (historyId != null) {
                    db.collection("Booking_History").document(historyId).delete();
                }

                db.collection("Booking_History").document()
                        .set(bookingInfo)
                        .addOnSuccessListener(aVoid -> Log.d("Firestore", "DocumentSnapshot successfully written!"))
                        .addOnFailureListener(e -> Log.w("Firestore", "Error writing document", e));

                startActivity(new_intent);
            });

            dialog.show();
            SystemUiHelper.enableImmersiveMode(this);

        });
    }

    void InitView() {
        fab = findViewById(R.id.fab);
        backBtn = findViewById(R.id.back_button);
        rvSeat = findViewById(R.id.seat_recycler_view);
        movieImg = findViewById(R.id.movie_image_background);
        title = findViewById(R.id.movie_title_text);
        Date = findViewById(R.id.date);
        Time = findViewById(R.id.time);
        Cinema = findViewById(R.id.location);
        numberSeat = findViewById(R.id.number_seat);
        totalMoney = findViewById(R.id.total_money);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SystemUiHelper.enableImmersiveMode(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ((!booked) && change && bookedSeats.size() != 0) {
            Log.d("SeatBookingScreen", "onStop: " + bookedSeats.size());
            super.onStop();
            List<Map<String, Object>> seatToSave = new ArrayList<>();

            for (Seat seat : bookedSeats) {
                Map<String, Object> seatMap = new HashMap<>();
                seatMap.put("id", seat.id);
                if (bookedSeats.contains(seat)) {
                    seatMap.put("status", "booked");
                } else {
                    seatMap.put("status", seat.status);
                }
                seatToSave.add(seatMap);
            }

            Map<String, Object> bookingInfo = new HashMap<>();
            bookingInfo.put("seats", seatToSave);
            bookingInfo.put("movieName", title_movie);
            bookingInfo.put("showTime", time);
            bookingInfo.put("showDate", date);
            bookingInfo.put("theaterName", cinema);
            bookingInfo.put("totalPrice", choosenSeatPrice);
            bookingInfo.put("totalSeat", choosenSeat);
            bookingInfo.put("movie_poster_path", poster_path);
            bookingInfo.put("status", false);

            if (historyId != null) {
                db.collection("Booking_History").document(historyId).delete();
            }

            db.collection("Booking_History").document()
                    .set(bookingInfo)
                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "DocumentSnapshot successfully written!"))
                    .addOnFailureListener(e -> Log.w("Firestore", "Error writing document", e));
        }
    }

    @Override
    public void onBackPressed() {

    }
}