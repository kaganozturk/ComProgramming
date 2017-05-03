package kagan.comprogramming;

import java.io.Serializable;

/**
 * Created by kagan on 1.05.2017.
 */

public class Person implements Serializable {

    private String name;
    private String lastName;
    private String city;
    private int imageResourceId;

    public Person(String name, String lastName, String city) {
        this.name = name;
        this.city = city;
        this.lastName = lastName;
        imageResourceId = R.drawable.movie;
    }


    @Override
    public String toString() {
        return name + " " + lastName;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
