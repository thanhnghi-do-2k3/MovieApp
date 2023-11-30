package com.example.myapplication.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AllMovieAdapter;
import com.example.myapplication.models.DataManager;
import com.example.myapplication.models.MovieInfo;
import com.example.myapplication.utils.SystemUiHelper;

import java.util.List;

public class AllMovieScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_movie_screen);
        SystemUiHelper.enableImmersiveMode(this);

        List<MovieInfo> allMovieList = DataManager.getInstance(this).allMovies;

        RecyclerView recyclerView = findViewById(R.id.all_movie_recycler_view);
        AllMovieAdapter allMovieAdapter = new AllMovieAdapter(allMovieList,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(allMovieAdapter);
    }
}