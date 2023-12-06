package com.example.mymovieapp.Screen;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovieapp.API.TMDB_API_Manager;
import com.example.mymovieapp.R;
import com.example.mymovieapp.adapter.AllMovieAdapter;
import com.example.mymovieapp.models.MovieInfo;
import com.example.mymovieapp.models.MovieResponse;
import com.example.mymovieapp.UIHelper.SystemUiHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMovieScreen extends AppCompatActivity {

    private List<MovieInfo> items = new ArrayList<>();
    private List<MovieInfo> customItems = null;
    private int current_all_movie_page = 2;
    private int current_search_page = 2;
    private boolean searchMode = false;
    private ImageView backButton;
    private RecyclerView recyclerView;
    private EditText searchBar;
    private ImageView menu;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_movie_screen);
        InitView();

        for (int i = 1; i <= 2; i++) {
            TMDB_API_Manager.getPopularMovies(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
                        items.addAll(response.body().results);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            }, i);
        }

        searchBar.addTextChangedListener(new TextWatcher() {
            private Handler handler = new Handler(Looper.getMainLooper());
            private Runnable runnable;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(runnable);
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        String searchText = searchBar.getText().toString();
                        if (searchText.length() > 0) {
                            searchMode = true;
                            current_all_movie_page = 2;
                            TMDB_API_Manager.searchMovies(new Callback<MovieResponse>() {
                                @Override
                                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                    if (response.isSuccessful()) {
                                        customItems = response.body().results;
                                        recyclerView.setAdapter(new AllMovieAdapter(customItems, AllMovieScreen.this));
                                    }
                                }

                                @Override
                                public void onFailure(Call<MovieResponse> call, Throwable t) {
                                    Log.d("Error", t.getMessage());
                                }
                            }, searchText);
                        } else {
                            searchMode = false;
                            current_search_page = 2;
                            recyclerView.setAdapter(new AllMovieAdapter(items, AllMovieScreen.this));
                        }
                    }
                };
                handler.postDelayed(runnable, 500);
            }
        });
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    View view = getCurrentFocus();
                    in.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    private void InitView() {
        backButton = findViewById(R.id.back_button);
        recyclerView = findViewById(R.id.all_movie_recycler_view);
        searchBar = findViewById(R.id.search_bar);
        menu = findViewById(R.id.Menu);
        drawerLayout = findViewById(R.id.drawerLayout);

        menu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        AllMovieAdapter allMovieAdapter = new AllMovieAdapter(items, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(allMovieAdapter);

        backButton.setOnClickListener(v -> {
            finish();
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLastItemDisplaying(recyclerView)) {
                    if (searchMode) {
                        current_search_page++;
                        TMDB_API_Manager.searchMovies(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                if (response.isSuccessful()) {
                                    customItems.addAll(response.body().results);
                                    recyclerView.getAdapter().notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {
                                Log.d("Error", t.getMessage());
                            }
                        }, searchBar.getText().toString(), current_search_page);
                    } else {
                        current_all_movie_page++;
                        TMDB_API_Manager.getPopularMovies(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                if (response.isSuccessful()) {
                                    items.addAll(response.body().results);
                                    recyclerView.getAdapter().notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {
                                Log.d("Error", t.getMessage());
                            }
                        }, current_all_movie_page);
                    }
                }
            }

            private boolean isLastItemDisplaying(RecyclerView recyclerView) {
                if (recyclerView.getAdapter().getItemCount() != 0) {
                    int lastVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                        return true;
                }
                return false;
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