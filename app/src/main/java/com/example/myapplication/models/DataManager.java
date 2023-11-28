package com.example.myapplication.models;

import android.util.Log;

import com.example.myapplication.sampledata.JsonFile;
import com.example.myapplication.utils.JsonReader;
import com.google.gson.Gson;

import java.util.List;

public class DataManager {
    private static DataManager instance = null;
    public List<MovieInfo> popularMovies;
    public List<MovieInfo> allMovies;
    public List<MovieInfo> topRatedMovies;

    JsonFile jsonFile = new JsonFile();
    void generatePopularMovies() {
        String jsonData = jsonFile.jsonPopular;
        Log.d("DataManager", "jsonData: " + jsonData.length());
        Gson gson = new Gson();
        MovieResponse movieResponse = gson.fromJson(jsonData, MovieResponse.class);
        popularMovies = movieResponse.results;
    }
    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
            instance.generatePopularMovies();
        }
        return instance;
    }

}
