package com.example.mymovieapp.API;

import com.example.mymovieapp.models.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TMDB_API_Manager {
    private static TMDB_API_Interface tmdbApiInterface = null;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "b03ae295fe14062bf8f1c3e260144fde";

    public static TMDB_API_Interface getInstance() {
        if (tmdbApiInterface == null) {
            tmdbApiInterface = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TMDB_API_Interface.class);
        }
        return tmdbApiInterface;
    }

    public static void getPopularMovies(Callback<MovieResponse> callback, int page) {
        Call<MovieResponse> call = TMDB_API_Manager.getInstance().getPopularMovies(API_KEY, page);
        call.enqueue(callback);
    }

    public static void searchMovies(Callback<MovieResponse> callback, String searchText, int page) {
        Call<MovieResponse> call = TMDB_API_Manager.getInstance().searchMovie(API_KEY, searchText, page);
        call.enqueue(callback);
    }

    public static void searchMovies(Callback<MovieResponse> callback, String searchText) {
        Call<MovieResponse> call = TMDB_API_Manager.getInstance().searchMovie(API_KEY, searchText);
        call.enqueue(callback);
    }
}
