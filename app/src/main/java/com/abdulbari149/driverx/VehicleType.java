package com.abdulbari149.driverx;

import android.graphics.drawable.shapes.OvalShape;

import androidx.annotation.NonNull;

public enum VehicleType {
    /** A precise car. */
    CAR("car"),
    /** A precise bike. */
    BIKE("bike"),
    /** A precise car premium. */
    CAR_PREMIUM("car_pre"),
    /** A precise auto. */
    AUTO("auto");

    private final String vehicleType;

    VehicleType(final String vehicleType) {
        this.vehicleType = vehicleType;
    }


    @NonNull
    @Override
    public String toString() {
        return vehicleType;
    }


}
