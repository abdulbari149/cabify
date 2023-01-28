package com.abdulbari149.driverx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abdulbari149.driverx.model.Driver;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//
//.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//@Override
//public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//        if (error != null) {
//        Log.e("firebase error:", error.getMessage());
//        return;
//        }
//
//        Log.d("Data Fetching...", "Drivers are being fetched");
//
//        assert value != null;
//        List<DocumentSnapshot> documentSnapshotList = value.getDocuments();
//        for (DocumentSnapshot doc: documentSnapshotList) {
//
//        Toast.makeText(DriversActivity.this, driver.toString(), Toast.LENGTH_SHORT).show();
//        driversList.add(driver);
//        }
//        adapter.notifyDataSetChanged();
//        }
//        });

public class DriversActivity extends AppCompatActivity {
    ArrayList<Driver> driversList;
    FirebaseFirestore db ;
    CollectionReference driversRef;
    DriversAdapter adapter;
    ProgressBar loading;

    private final OnSuccessListener<QuerySnapshot> getDriversSuccess = new OnSuccessListener<QuerySnapshot>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            if (!queryDocumentSnapshots.isEmpty()) {
                loading.setVisibility(View.GONE);
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot doc : documents) {
                    String id = doc.getId();
                    String name = doc.get("name", String.class);
                    Boolean available = doc.get("available", Boolean.class);
                    URL image = null;
                    try {
                        image = new URL(doc.get("image", String.class));
                    } catch (MalformedURLException e) {
                        return;
                    }
                    Driver driver = new Driver(id, name, image, available);
                    driversList.add(driver);
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(DriversActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private final OnFailureListener getDriversFailure =  new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(DriversActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
        }
    };

    private void getDrivers(String vehicleType) {
        db.collection("drivers")
                .whereEqualTo("vehicleType", vehicleType)
                .get()
                .addOnSuccessListener(DriversActivity.this, getDriversSuccess)
                .addOnFailureListener(DriversActivity.this, getDriversFailure);
    };

    private final AdapterView.OnItemClickListener driverItemClickListener =  new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(new Intent(DriversActivity.this, LocationActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);

        String vehicleType = getIntent().getStringExtra("vehicle_type");

        ((TextView)findViewById(R.id.drivers_title)).setText(vehicleType.toUpperCase(Locale.ROOT));
        RecyclerView listView = (RecyclerView) findViewById(R.id.drivers_list);
        loading = (ProgressBar) findViewById(R.id.drivers_progress_bar);

        db = FirebaseFirestore.getInstance();
        driversRef = db.collection("drivers");
        driversList = new ArrayList<Driver>();
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listView.setHasFixedSize(true);
        adapter = new DriversAdapter(DriversActivity.this, driversList, new DriversAdapter.OnItemClickListener() {
            @Override public void onItemClick(Driver item) {
                Intent driverIntent = new Intent(DriversActivity.this, LocationActivity.class);
                driverIntent.putExtra("driverId", item.getId());
                startActivity(driverIntent);
            }
        });
        listView.setAdapter(adapter);
        getDrivers(vehicleType);
    }

}