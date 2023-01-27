package com.example.cabify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.UUID;

public class VehicleActivity extends AppCompatActivity {
    List<Driver> driversList;

    private final OnSuccessListener<QuerySnapshot> getDriversSuccess = new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot document : documents) {
                UUID id = UUID.fromString(document.getId());
                String name = document.get("name", String.class);
                Boolean available = Boolean.TRUE.equals(document.get("available", Boolean.class));
                String image = document.get("image", String.class);
//                Driver driver = new Driver(id, name, available, image);
//                driversList.add(driver);
                Log.d("data: ",  id.toString());
            }
        }
    };

    private final OnFailureListener getDriversFailure = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
    };

    private void getDrivers(String vehicleType) {
        FirebaseFirestore.getInstance().collection("drivers")
                .whereEqualTo("vehicleType", vehicleType)
                .get()
                .addOnSuccessListener(this.getDriversSuccess)
                .addOnFailureListener(this.getDriversFailure);
    };

    private final AdapterView.OnItemClickListener driverItemClickListener =  new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(new Intent(VehicleActivity.this, LocationActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        String vehicleType = getIntent().getStringExtra("vehicle_type");
        getDrivers(vehicleType);

        ListView listView = (ListView) findViewById(R.id.vehicle_list);
//        DriversAdapter driversAdapter = new DriversAdapter(getApplicationContext(), driversList);
//        listView.setAdapter(driversAdapter);
//        listView.setOnItemClickListener(driverItemClickListener);
    }
}