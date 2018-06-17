package org.tensorflow.demo.model;

/**
 * Created by mohamed fathy on 12/10/2017.
 */

public class Rooms {
    private String Name;
    private String Image;

    public Rooms() {
    }

    public Rooms(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
