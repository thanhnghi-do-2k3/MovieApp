package com.example.myapplication.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.UIHelper.SliderTransformer;
import com.example.myapplication.adapter.CardStackAdapter;
import com.example.myapplication.adapter.MovieStackAdapter;
import com.example.myapplication.adapter.MoviesCardAdapter;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.MovieInfo;
import com.example.myapplication.utils.SystemUiHelper;


import java.util.List;

public class HomeScreen extends AppCompatActivity {
    TextView view_all_text;
    ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        view_all_text = findViewById(R.id.tvViewAll);
        avatar = findViewById(R.id.Avatar);

        List<MovieInfo> popular_movies = DataManager.getInstance(this).popularMovies;
        List<MovieInfo> now_playing_movies = DataManager.getInstance(this).nowPlayingMovies;

        Log.d("MainActivity", "popular_movies: " + now_playing_movies.size());

        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        MovieStackAdapter adapter = new MovieStackAdapter(popular_movies,this);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        viewPager2.setPageTransformer(new SliderTransformer(3));

        RecyclerView recyclerView = findViewById(R.id.card_recylce_view);
        MoviesCardAdapter moviesCardAdapter = new MoviesCardAdapter(now_playing_movies,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(moviesCardAdapter);

        view_all_text.setOnClickListener(v -> {
            Intent intent = new Intent(this, AllMovieScreen.class);
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