package kagan.comprogramming;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kagan on 16.04.2017.
 */

public class Group implements Serializable {

    private String title;
    private int imageResourceId;
    private ArrayList<Person> members;

    public Group(String title, int imageResourceId) {
        this.title = title;
        this.imageResourceId = imageResourceId;
        members = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            members.add(new Person("ahmet", "erdem", "antalya"));
        }
    }

    public Group(String title) {
        this(title, 0);
    }

    @Override
    public String toString() {
        return title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public ArrayList<Person> getMembers() {
        return members;
    }
}
