package com.example.myapplication.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DatePickingAdapter;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.MovieInfo;
import com.example.myapplication.utils.SystemUiHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MoviePageScreen extends AppCompatActivity {

    private ImageView MovieImg;
    private TextView MovieTitle;
    private TextView MovieDescription;
    private LinearLayout MovieGenre;
    private RecyclerView DatepickerRecycler;
    private RecyclerView TimepickerRecycler[] = new RecyclerView[4];
    private ImageView backBtn;
    private FloatingActionButton fab;
    private MovieInfo movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_page_screen);

        movie = new MovieInfo();
        initView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Log.d("MoviePageScreen", "bundle: " + bundle.toString());
            movie = (MovieInfo) bundle.getSerializable("movie");
        }

        Integer src = DataManager.getInstance(this).getMovieImgSrc(movie);
        if (src != 0) {
            MovieImg.setImageResource(src);
        } else {
            MovieImg.setImageResource(R.drawable.no_img_avai);
        }

        Log.d("MoviePageScreen", "movie: " + movie.title);
        MovieTitle.setText(movie.title);
        MovieDescription.setText(movie.overview);
        DatePickingAdapter datePickingAdapter = new DatePickingAdapter(this);
        DatepickerRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        DatepickerRecycler.setAdapter(datePickingAdapter);

        for (int i = 0; i < 4; i++) {
            TimepickerRecycler[i].setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            TimepickerRecycler[i].setAdapter(new com.example.myapplication.adapter.TimePickingAdapter(this));
        }
    }

    private void initView() {
        MovieImg = (ImageView) findViewById(R.id.movie_image_background);
        MovieTitle = (TextView) findViewById(R.id.movie_title_text);
        MovieDescription = (TextView) findViewById(R.id.movie_description);
        MovieGenre = (LinearLayout) findViewById(R.id.movie_genre);
        DatepickerRecycler = (RecyclerView) findViewById(R.id.get_day_recycleview);
        TimepickerRecycler[0] = (RecyclerView) findViewById(R.id.recyclerView_showTimes1);
        TimepickerRecycler[1] = (RecyclerView) findViewById(R.id.recyclerView_showTimes2);
        TimepickerRecycler[2] = (RecyclerView) findViewById(R.id.recyclerView_showTimes3);
        TimepickerRecycler[3] = (RecyclerView) findViewById(R.id.recyclerView_showTimes4);
        backBtn = (ImageView) findViewById(R.id.back_button);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        MovieImg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // This listener is called once the layout is complete.
                MovieImg.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Calculate the scale ratio based on the ImageView's width and the drawable's intrinsic width.
                Drawable drawable = MovieImg.getDrawable();
                float scaleRatio = (float) MovieImg.getWidth() / (float) drawable.getIntrinsicWidth();

                // Create a new Matrix object and set the scale, keeping the top left corner aligned.
                Matrix matrix = new Matrix();
                matrix.setScale(scaleRatio, scaleRatio, 0, 0); // Scales from the top left corner (0,0)
                MovieImg.setImageMatrix(matrix);

                // Set the scale type to MATRIX to apply the custom Matrix.
                MovieImg.setScaleType(ImageView.ScaleType.MATRIX);
            }
        });

        backBtn.setOnClickListener(v -> {
            finish();
        });

        fab.setOnClickListener(v -> {
            Bundle extra = new Bundle();
            extra.putSerializable("movie", movie);
            Intent intent = new Intent(this, SeatBookingScreen.class);
            intent.putExtras(extra);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SystemUiHelper.enableImmersiveMode(this);
    }

    @Override
    public void onBackPressed() {

    }

}