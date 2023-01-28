package com.abdulbari149.driverx;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {
    public String pickup;
    public String dropoff;
    public Button confirm_button;
    public Button cancel_button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        confirm_button = (Button) findViewById(R.id.confirm_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        Bundle bundle = getIntent().getParcelableExtra("args");
        LatLng pickup_loc = bundle.getParcelable("loc1");
        LatLng dropoff_loc = bundle.getParcelable("loc2");
        double distance = bundle.getDouble("distance");


        PriceCalculator calculator = new PriceCalculator(0.2);
        double price = calculator.calculatePrice(distance);


        double pick_latitude = pickup_loc.latitude;
        double pick_longitude = pickup_loc.longitude;
        double drop_latitude = dropoff_loc.latitude;
        double drop_longitude = dropoff_loc.longitude;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    try {
        List<Address> addresses_pickup = geocoder.getFromLocation(pick_latitude, pick_longitude, 1);
        if (addresses_pickup.size() > 0){
            pickup = addresses_pickup.get(0).getAddressLine(0);
            Log.d("Pickup address",pickup);
        }
    }
    catch (IOException e){
        e.printStackTrace();
    }

        try {
            List<Address> addresses_dropoff = geocoder.getFromLocation(drop_latitude, drop_longitude, 1);
            if (addresses_dropoff.size() > 0){
                dropoff = addresses_dropoff.get(0).getAddressLine(0);
                Log.d("Drop off address",dropoff);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        TextView pickup_value = (TextView) findViewById(R.id.PickupLoc_value);
        TextView dropoff_value = (TextView) findViewById(R.id.Dropoffloc_value);
        TextView distance_value = (TextView) findViewById(R.id.Distance_value);
        TextView price_value = (TextView) findViewById(R.id.Price_value);

        pickup_value.setText(pickup);
        dropoff_value.setText(dropoff);
        double round_distance = Math.round(distance * Math.pow(10 , 0));
        String Distance = Double.toString(round_distance);
        double round_price = Math.round(price * Math.pow(10 , 0));
        String Price = Double.toString(round_price);
        distance_value.setText(Distance.concat(" meters"));
        price_value.setText("Rs. ".concat(Price));

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_cancel = new Intent(ConfirmationActivity.this , OptionsActivity.class);
                startActivity(intent_cancel);
            }

        });
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
