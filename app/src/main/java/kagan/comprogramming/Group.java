package kagan.comprogramming;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kagan on 16.04.2017.
 */

public class Group implements Serializable {

    private int gruop_id;
    private String name;
    private String imageUri;
    private String description;
    private int category;
    private ArrayList<Person> members;

    public Group() {
        members = new ArrayList<>();

    }

    @Override
    public String toString() {
        return name;
    }


    public ArrayList<Person> getMembers() {
        return members;
    }

    public int getGruop_id() {
        return gruop_id;
    }

    public void setGruop_id(int gruop_id) {
        this.gruop_id = gruop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setMembers(ArrayList<Person> members) {
        this.members = members;
    }
}
