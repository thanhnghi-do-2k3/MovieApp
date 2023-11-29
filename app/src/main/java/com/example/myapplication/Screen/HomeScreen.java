package com.example.myapplication.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CardStackAdapter;
import com.example.myapplication.adapter.MoviesCardAdapter;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.MovieInfo;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.List;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        Button back_button = findViewById(R.id.stack_back_button);
        Button next_button = findViewById(R.id.stack_next_button);
        Button view_all_button = findViewById(R.id.all_movie_button);

        List<MovieInfo> popular_movies = DataManager.getInstance(this).popularMovies;
        List<MovieInfo> now_playing_movies = DataManager.getInstance(this).nowPlayingMovies;

        Log.d("MainActivity", "popular_movies: " + now_playing_movies.size());

        CardStackLayoutManager layoutManager = new CardStackLayoutManager(this);
        layoutManager.setStackFrom(StackFrom.BottomAndRight);
        layoutManager.setVisibleCount(3);
//        layoutManager.setTranslationInterval(16);
        CardStackAdapter adapter = new CardStackAdapter(popular_movies,this);
        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        cardStackView.setLayoutManager(layoutManager);
        cardStackView.setAdapter(adapter);

        back_button.setOnClickListener(v -> {
            RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .build();
            layoutManager.setRewindAnimationSetting(setting);
            cardStackView.rewind();
        });

        next_button.setOnClickListener(v -> {
            cardStackView.swipe();
        });

        RecyclerView recyclerView = findViewById(R.id.card_recylce_view);
        MoviesCardAdapter moviesCardAdapter = new MoviesCardAdapter(now_playing_movies,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(moviesCardAdapter);

        view_all_button.setOnClickListener(v -> {
            Log.d("HomeScreen", "view_all_button: ");
            Intent intent = new Intent(this, AllMovieScreen.class);
            startActivity(intent);
        });
    }
}