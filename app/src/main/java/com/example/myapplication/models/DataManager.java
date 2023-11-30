package com.example.myapplication.models;


import static android.text.TextUtils.replace;

import android.content.Context;
import android.graphics.Movie;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.utils.JsonReader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataManager {
    private static DataManager instance = null;
    public List<MovieInfo> popularMovies;
    public List<MovieInfo> nowPlayingMovies;
    public List<MovieInfo> allMovies;
    public List<Seat> seats;
    private JsonReader jsonReader = new JsonReader();
    private Context context;

    void generatePopularMovies() {
        String jsonData = jsonReader.loadJSONFromAsset(context, "Json/popular.json");
        Log.d("DataManager", "jsonData: " + jsonData.length());
        Gson gson = new Gson();
        MovieResponse movieResponse = gson.fromJson(jsonData, MovieResponse.class);
        popularMovies = movieResponse.results;
    }

    void generateNowPlayingMovies() {
        String jsonData = jsonReader.loadJSONFromAsset(context, "Json/now_playing.json");
        Log.d("DataManager", "jsonData: " + jsonData.length());
        Gson gson = new Gson();
        MovieResponse movieResponse = gson.fromJson(jsonData, MovieResponse.class);
        nowPlayingMovies = movieResponse.results;
    }

    void generateSeats() {
        String jsonData = jsonReader.loadJSONFromAsset(context, "Json/seats.json");
        Log.d("DataManager", "jsonData: " + jsonData.length());
        Gson gson = new Gson();
        Type seatListType = new TypeToken<List<Seat>>(){}.getType();
        seats = gson.fromJson(jsonData, seatListType);
    }

    private DataManager(Context context) {
        this.context = context;
    }

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
            instance.generatePopularMovies();
            instance.generateNowPlayingMovies();
            instance.allMovies = Stream.concat(instance.popularMovies.stream(), instance.nowPlayingMovies.stream()).collect(Collectors.toList());
            instance.generateSeats();
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

    public Integer getMovieImgSrc(MovieInfo movie) {
        String res = (movie.poster_path).substring(1).toLowerCase().replace(".jpg", "");
        res = '_' + res;
        int resID = context.getResources().getIdentifier(res, "drawable", context.getPackageName());
        Log.d("DataManager", "resID: " + resID);
        return resID;
    }

}
