package com.example.cabify;

import androidx.annotation.NonNull;

import java.util.UUID;


public class Driver {
    private UUID id;

    private String name;

    private String image;

    private boolean available;

    private VehicleType vehicleType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    Driver(
            UUID id,
            String name,
            Boolean available,
            String image
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", available=" + available +
                ", vehicleType=" + vehicleType +
                '}';
    }
}
