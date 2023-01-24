package com.example.cabify;

public class PriceCalculator {
    private double pricePerMetre;

    public PriceCalculator(double pricePerMetre) {
        this.pricePerMetre = pricePerMetre;
    }

    public double calculatePrice(double distanceInKm) {
        return distanceInKm * pricePerMetre;
    }
}

