package com.example.myapplication.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SeatAdapter;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.MovieInfo;
import com.example.myapplication.models.Seat;
import com.example.myapplication.utils.SystemUiHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SeatBookingScreen extends AppCompatActivity {
    private FloatingActionButton fab;
    private ImageView backBtn;
    private RecyclerView rvSeat;
    private ImageView movieImg;
    private TextView title;
    private MovieInfo movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_booking_screen);
        SystemUiHelper.enableImmersiveMode(this);

        InitView();
        List<Seat> items = DataManager.getInstance(this).seats;

        SeatAdapter adapter = new SeatAdapter(this, items);
        rvSeat.setLayoutManager(new GridLayoutManager(this, 8));
        rvSeat.setAdapter(adapter);

        backBtn.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Log.d("MoviePageScreen", "bundle: " + bundle.toString());
            movie = (MovieInfo) bundle.getSerializable("movie");
        }

        Integer src = DataManager.getInstance(this).getMovieImgSrc(movie);
        if (src != 0) {
            movieImg.setImageResource(src);
        } else {
            movieImg.setImageResource(R.drawable.no_img_avai);
        }

        title.setText(movie.title);
    }

    void InitView() {
        fab = findViewById(R.id.fab);
        backBtn = findViewById(R.id.back_button);
        rvSeat = findViewById(R.id.seat_recycler_view);
        movieImg = findViewById(R.id.movie_image_background);
        title = findViewById(R.id.movie_title_text);

        movieImg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // This listener is called once the layout is complete.
                movieImg.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Calculate the scale ratio based on the ImageView's width and the drawable's intrinsic width.
                Drawable drawable = movieImg.getDrawable();
                float scaleRatio = (float) movieImg.getWidth() / (float) drawable.getIntrinsicWidth();

                // Create a new Matrix object and set the scale, keeping the top left corner aligned.
                Matrix matrix = new Matrix();
                matrix.setScale(scaleRatio, scaleRatio, 0, 0); // Scales from the top left corner (0,0)
                movieImg.setImageMatrix(matrix);

                // Set the scale type to MATRIX to apply the custom Matrix.
                movieImg.setScaleType(ImageView.ScaleType.MATRIX);
            }
        });
    }
}