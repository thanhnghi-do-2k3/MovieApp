package com.example.myapplication.models;


import static android.text.TextUtils.replace;

import android.content.Context;
import android.graphics.Movie;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.sampledata.JsonFile;
import com.example.myapplication.utils.JsonReader;
import com.google.gson.Gson;

import java.util.List;

public class DataManager {
    private static DataManager instance = null;
    public List<MovieInfo> popularMovies;
    public List<MovieInfo> nowPlayingMovies;
    public List<MovieInfo> topRatedMovies;

    private Context context;

    JsonFile jsonFile = new JsonFile();
    void generatePopularMovies() {
        String jsonData = jsonFile.jsonPopular;
        Log.d("DataManager", "jsonData: " + jsonData.length());
        Gson gson = new Gson();
        MovieResponse movieResponse = gson.fromJson(jsonData, MovieResponse.class);
        popularMovies = movieResponse.results;
    }

    void generateNowPlayingMovies() {
        String jsonData = jsonFile.jsonNowPlaying;
        Log.d("DataManager", "jsonData: " + jsonData.length());
        Gson gson = new Gson();
        MovieResponse movieResponse = gson.fromJson(jsonData, MovieResponse.class);
        nowPlayingMovies = movieResponse.results;
    }

    private DataManager(Context context) {
        this.context = context;
    }

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
            instance.generatePopularMovies();
            instance.generateNowPlayingMovies();
        }
        return instance;
    }

    public Integer getMovieImgSrc(int position, List<MovieInfo> movies) {
        String res = (movies.get(position).poster_path).substring(1).toLowerCase().replace(".jpg", "");
        res = '_' + res;
        int resID = context.getResources().getIdentifier(res, "drawable", context.getPackageName());
        Log.d("DataManager", "resID: " + resID);
        return resID;
    }

}
