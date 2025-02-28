package com.example.mymovieapp.models;

public class Seat {
    public int id;
    public String status;
    public String booking_time;

    public boolean getStatus() {
        return this.status.equals("available");
    }
}
