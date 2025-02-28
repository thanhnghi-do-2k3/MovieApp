package com.example.mymovieapp.models;

import java.util.ArrayList;
import java.util.List;


public class History {

    public String movieName;
    public String movie_img;
    public String showDate;
    public String showTime;
    public String theaterName;
    public String totalSeat;
    public String totalPrice;
    public boolean status;
    public String id;
    public String movie_poster_path;
    public List<Seat> seats = new ArrayList<>();

    public History() {

    }

    public History(String movie_name, String movie_img, String movie_date, String movie_time, String movie_cinema, String movie_seat, String movie_price, boolean status) {
        this.movieName = movie_name;
        this.movie_img = movie_img;
        this.showDate = movie_date;
        this.showTime = movie_time;
        this.theaterName = movie_cinema;
        this.totalSeat = movie_seat;
        this.totalPrice = movie_price;
        this.status = status;
    }

//    @PropertyName("movieName")
//    private String movieName;
//    private String movie_img;
//    @PropertyName("showDate")
//    private String showDate;
//    @PropertyName("showTime")
//    private String showTime;
//    @PropertyName("theaterName")
//    private String theaterName;
//    @PropertyName("totalSeat")
//    private String totalSeat;
//    @PropertyName("totalPrice")
//    private String totalPrice;
//    @PropertyName("status")
//    private boolean status;
//    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movie_name) {
        this.movieName = movie_name;
    }

    public String getMovieImg() {
        return movie_img;
    }

    public void setMovieImg(String movie_img) {
        this.movie_img = movie_img;
    }

    public String getMovieDate() {
        return showDate;
    }

    public void setShowDate(String movie_date) {
        this.showDate = movie_date;
    }

    public String getMovieTime() {
        return showTime;
    }


    public void setShowTime(String movie_time) {
        this.showTime = movie_time;
    }

    public String getMovieCinema() {
        return theaterName;
    }

    public void setTheaterName(String movie_cinema) {
        this.theaterName = movie_cinema;
    }

    public String getMovieSeat() {
        return totalSeat;
    }

    public void setTotalSeat(String movie_seat) {
        this.totalSeat = movie_seat;
    }

    public String getMoviePrice() {
        return totalPrice;
    }

    public void setTotalPrice(String movie_price) {
        this.totalPrice = movie_price;
    }
}

