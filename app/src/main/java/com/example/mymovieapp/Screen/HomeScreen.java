package com.example.mymovieapp.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mymovieapp.API.TMDB_API_Manager;
import com.example.mymovieapp.R;
import com.example.mymovieapp.UIHelper.SliderTransformer;
import com.example.mymovieapp.adapter.MovieStackAdapter;
import com.example.mymovieapp.adapter.MoviesCardAdapter;
import com.example.mymovieapp.models.MovieInfo;
import com.example.mymovieapp.models.MovieResponse;
import com.example.mymovieapp.UIHelper.SystemUiHelper;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen extends AppCompatActivity {
    TextView view_all_text;
    ImageView avatar;
    ImageView menu;
    RecyclerView recyclerView;
    ViewPager2 viewPager2;

    private DrawerLayout drawerLayout;
    private NavigationView nav;


    private List<MovieInfo> popular_movies = new ArrayList<>();
    private List<MovieInfo> now_playing_movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        InitView();

        TMDB_API_Manager.getPopularMovies(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponses = response.body();
                    if (movieResponses == null) {

                    } else {
                        popular_movies.addAll(movieResponses.results);
                        viewPager2.getAdapter().notifyDataSetChanged();
                    }

                } else {
                    Log.d("HomeScreen", "onResponse: " + "fail");
                    Log.d("HomeScreen", "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("HomeScreen", "onFailure: " + t.getMessage());
            }
        }, 1);

        TMDB_API_Manager.getPopularMovies(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponses = response.body();
                    if (movieResponses == null) {

                    } else {
                        now_playing_movies.addAll(movieResponses.results);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }

                } else {
                    Log.d("HomeScreen", "onResponse: " + "fail");
                    Log.d("HomeScreen", "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("HomeScreen", "onFailure: " + t.getMessage());
            }
        }, 2);

        AdapterSetting();
    }

    private void AdapterSetting() {
        MovieStackAdapter adapter = new MovieStackAdapter(popular_movies, this);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        viewPager2.setPageTransformer(new SliderTransformer(3));

        MoviesCardAdapter moviesCardAdapter = new MoviesCardAdapter(now_playing_movies, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(moviesCardAdapter);

        view_all_text.setOnClickListener(v -> {
            Intent intent = new Intent(this, AllMovieScreen.class);
            startActivity(intent);
        });
    }

    private void InitView() {
        view_all_text = findViewById(R.id.tvViewAll);
        avatar = findViewById(R.id.Avatar);
        recyclerView = findViewById(R.id.card_recylce_view);
        viewPager2 = findViewById(R.id.viewPager);
        menu = findViewById(R.id.Menu);
        drawerLayout = findViewById(R.id.drawerLayout);
        nav = findViewById(R.id.navigationView);

        drawerLayout.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                Log.d("HomeScreen", "InitView: " + "close");
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                Log.d("HomeScreen", "InitView: " + "open");
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        menu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                Log.d("HomeScreen", "InitView: " + "close");
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                Log.d("HomeScreen", "InitView: " + "open");
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        nav.setOnClickListener(v -> {
            Log.d("HomeScreen", "InitView: " + "booking history");
        });

        nav.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Log.d("HomeScreen", "InitView: " + id);
            if (id == R.id.history) {
                Log.d("HomeScreen", "InitView: " + "booking history");
                DrawerLayout drawer = findViewById(R.id.drawerLayout);
                drawer.closeDrawer(GravityCompat.END);
                Intent intent = new Intent(this, HistoryScreen.class);
                startActivity(intent);
            }
            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SystemUiHelper.enableImmersiveMode(this);
    }

    @Override
    @SuppressWarnings("unused")
    public void onBackPressed() {

    }
}