package kagan.comprogramming;

import android.icu.util.Calendar;

/**
 * Created by kagan on 1.05.2017.
 */

public class Event {

    private String name;
    private String city;
    private String address;
    private Calendar date;
    private String category;


    public Event(String name, String city, String address, String category) {
        this.name = name;
        this.city = city;
        this.address = address;
        //this.date = date;
    }

    public String getName() {
        return name;
    }

    public Calendar getCalendar() {
        return date;
    }

    public String getCity() {
        return city;
    }
}
