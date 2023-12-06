package com.example.mymovieapp.API;

import com.example.mymovieapp.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDB_API_Interface {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("search/movie")
    Call<MovieResponse> searchMovie(@Query("api_key") String api_key, @Query("query") String query, @Query("page") int page);

    @GET("search/movie")
    Call<MovieResponse> searchMovie(@Query("api_key") String api_key, @Query("query") String query);
}
