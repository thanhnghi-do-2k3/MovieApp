package com.example.mymovieapp.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.mymovieapp.R;
import com.example.mymovieapp.adapter.DatePickingAdapter;
import com.example.mymovieapp.adapter.TimePickingAdapter;
import com.example.mymovieapp.models.MovieInfo;
import com.example.mymovieapp.UIHelper.SystemUiHelper;
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
    private boolean time_check = false;
    private boolean date_check = false;
    private String time = "";
    private String cinema = "";
    private String date = "";
    private final String[] cinema_name = {
            "Sathyam Cinemas: Royapettah",
            "Escape Cinemas: Royapettah",
            "Cineplex Movie",
            "Cineplex Movie"
    };

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

        String url = "https://image.tmdb.org/t/p/w500" + movie.poster_path;
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.no_img_avai)
                .override(MovieImg.getWidth(), Target.SIZE_ORIGINAL)
                .into(MovieImg);

        Log.d("MoviePageScreen", "movie: " + movie.title);
        MovieTitle.setText(movie.title);
        MovieDescription.setText(movie.overview);
        DatePickingAdapter datePickingAdapter = new DatePickingAdapter(this, new DatePickingAdapter.onClickItemListener() {
            @Override
            public void onItemClick(String Date) {
                date = Date;
                if (!date_check) {
                    date_check = true;
                }
            }

            @Override
            public void onItemClickRestored() {
                if (date_check) {
                    date_check = false;
                }
            }
        });

        DatepickerRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        DatepickerRecycler.setAdapter(datePickingAdapter);

        for (int i = 0; i < 4; i++) {
            TimepickerRecycler[i].setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            TimepickerRecycler[i].setAdapter(new com.example.mymovieapp.adapter.TimePickingAdapter(this, new TimePickingAdapter.onItemClickListener() {
                @Override
                public void onItemClick(TimePickingAdapter adapter, TimePickingAdapter.ViewHolder holder) {
                    for (int j = 0; j < 4; j++) {
                        if (j != adapter.getId()) {
                            ((TimePickingAdapter) TimepickerRecycler[j].getAdapter()).setSelectedState(true);
                        }
                    }
                    time = holder.time.getText().toString();
                    cinema = cinema_name[adapter.getId()];
                    if (!time_check) {
                        time_check = true;
                    }
                }

                @Override
                public void onItemClickRestored(TimePickingAdapter adapter) {
                    for (int j = 0; j < 4; j++) {
                        if (j != adapter.getId()) {
                            ((TimePickingAdapter) TimepickerRecycler[j].getAdapter()).setSelectedState(false);
                        }
                    }
                    if (time_check) {
                        time_check = false;
                    }
                }
            }, i));
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

        backBtn.setOnClickListener(v -> {
            finish();

        });

        fab.setOnClickListener(v -> {
            if (!date_check || !time_check) {
                if (!date_check) {
                    Toast.makeText(MoviePageScreen.this, "Please select date", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MoviePageScreen.this, "Please select time", Toast.LENGTH_SHORT).show();
                }
            } else {
                Bundle extra = new Bundle();
                extra.putSerializable("movie", movie);
                extra.putString("date", date);
                extra.putString("time", time);
                extra.putString("cinema", cinema);
                Intent intent = new Intent(this, SeatBookingScreen.class);
                intent.putExtras(extra);
                startActivity(intent);
            }
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