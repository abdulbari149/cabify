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

    private String image;

    private boolean available;


    private String vehicleType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Driver() {

    }


    public Driver(
            String name,
            String image,
            Boolean available,
            String vehicleType
    ) {
        this.name = name;
        this.available = available;
        this.image = image;
        this.vehicleType = vehicleType;
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
