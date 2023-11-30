package com.example.myapplication.models;

public class Seat {
    private int id;
    public String status;
    public String booking_time;

    public boolean getStatus() {
        return this.status.equals("available");
    }
}
