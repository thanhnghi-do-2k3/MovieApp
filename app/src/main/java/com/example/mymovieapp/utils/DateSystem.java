package com.example.mymovieapp.utils;

import java.util.Calendar;

public class DateSystem {
    private Calendar calendar = Calendar.getInstance();

    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public String getDayOfWeek() {
        String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return days[dayOfWeek - 1];
    }

    public int addDay(int day) {
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getDayOfWeekInt() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
