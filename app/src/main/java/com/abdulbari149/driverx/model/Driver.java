package com.abdulbari149.driverx.model;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.net.URL;


public class Driver {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    private String name;

    private URL image;

    private boolean available;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Driver(
            String id,
            String name,
            URL image,
            Boolean available
    ) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return "Driver{" +
                ", id = '" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", available=" + available +
                '}';
    }
}
