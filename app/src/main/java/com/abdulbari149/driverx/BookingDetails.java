package com.abdulbari149.driverx;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;

public class BookingDetails {
    private String pickup;
    private String dropoff;
    private GeoPoint pickup_loc;
    private GeoPoint dropoff_loc;
    private double distance;
    private double price;
    private DocumentReference driverId;
    private String passengerId;
    private String driverName;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }


    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }


    public DocumentReference getDriverId() {
        return driverId;
    }

    public void setDriverId(DocumentReference driverId) {
        this.driverId = driverId;
    }


    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public GeoPoint getPickup_loc() {
        return pickup_loc;
    }

    public void setPickup_loc(GeoPoint pickup_loc) {
        this.pickup_loc = pickup_loc;
    }

    public GeoPoint getDropoff_loc() {
        return dropoff_loc;
    }

    public void setDropoff_loc(GeoPoint dropoff_loc) {
        this.dropoff_loc = dropoff_loc;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BookingDetails() {

    }

    public BookingDetails(String pickup, String dropoff, GeoPoint pickup_loc, GeoPoint dropoff_loc, double distance, double price, DocumentReference driverId, String driverName) {
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.pickup_loc = pickup_loc;
        this.dropoff_loc = dropoff_loc;
        this.distance = distance;
        this.price = price;
        this.driverId = driverId;
        this.driverName = driverName;
    }

    @Override
    public String toString() {
        return "BookingDetails{" +
                "pickup='" + pickup + '\'' +
                ", dropoff='" + dropoff + '\'' +
                ", pickup_loc=" + pickup_loc +
                ", dropoff_loc=" + dropoff_loc +
                ", distance=" + distance +
                ", price=" + price +
                ", driverId=" + driverId +
                ", passengerId='" + passengerId + '\'' +
                '}';
    }
}
