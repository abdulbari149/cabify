package com.abdulbari149.driverx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {
    BookingDetails bookingDetails;
    FirebaseFirestore db;
    CollectionReference bookingsRef;

    private final View.OnClickListener OnConfirmBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(ConfirmationActivity.this, "Error Occurred. During booking Please try logging In", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ConfirmationActivity.this, MainActivity.class));
                return;
            }

            String userId = currentUser.getUid();
            bookingDetails.setPassengerId(userId);
            bookingsRef.add(bookingDetails)
                    .addOnSuccessListener(addBookingSuccess)
                    .addOnFailureListener(addBookingFailure);
        }
    };


    private final OnSuccessListener<DocumentReference> addBookingSuccess = new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
            Toast.makeText(ConfirmationActivity.this, "Your booking has been confirmed!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ConfirmationActivity.this, RidesActivity.class));
        }
    };

    private final OnFailureListener addBookingFailure = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(ConfirmationActivity.this, "Error Occurred during booking. Please try again", Toast.LENGTH_LONG).show();
        }
    };

    private final View.OnClickListener OnCancelBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_cancel = new Intent(ConfirmationActivity.this, OptionsActivity.class);
            startActivity(intent_cancel);
        }
    };

    private String getAddressFromLatLing(LatLng loc) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String address = null;
        List<Address> addressList = geocoder.getFromLocation(loc.latitude, loc.longitude, 1);
        address = addressList.get(0).getAddressLine(0);
        return address;
    }

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        findViewById(R.id.confirm_button).setOnClickListener(OnConfirmBtnClick);
        findViewById(R.id.cancel_button).setOnClickListener(OnCancelBtnClick);
        db = FirebaseFirestore.getInstance();
        bookingsRef = db.collection("bookings");

        String pickup = null, dropoff = null;
        Bundle bundle = getIntent().getParcelableExtra("args");
        LatLng pickup_loc = bundle.getParcelable("pickup");
        LatLng dropoff_loc = bundle.getParcelable("dropoff");
        double distance = bundle.getDouble("distance");
        String driverId = bundle.getString("driverId");
        String driverName = bundle.getString("driverName");
        distance = Math.round(distance * Math.pow(10, 0));

        PriceCalculator calculator = new PriceCalculator(0.2);
        double price = calculator.calculatePrice(distance);
        price = Math.round(price * Math.pow(10, 0));

        try {
            dropoff = getAddressFromLatLing(dropoff_loc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            pickup = getAddressFromLatLing(pickup_loc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DocumentReference driverRef = db.collection("drivers").document(driverId);

        bookingDetails = new BookingDetails(
                pickup,
                dropoff,
                new GeoPoint(pickup_loc.latitude, pickup_loc.longitude),
                new GeoPoint(dropoff_loc.latitude, dropoff_loc.longitude),
                distance,
                price,
                driverRef,
                driverName
        );

        ((TextView) findViewById(R.id.PickupLoc_value)).setText(pickup);
        ((TextView) findViewById(R.id.Dropoffloc_value)).setText(dropoff);
        ((TextView) findViewById(R.id.Distance_value)).setText(Double.toString(distance).concat(" meters"));
        ((TextView) findViewById(R.id.Price_value)).setText("Rs. ".concat(Double.toString(price)));

    }
}
