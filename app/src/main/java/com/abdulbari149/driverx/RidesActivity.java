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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class RidesActivity extends AppCompatActivity {
    ArrayList<BookingDetails> bookingDetailsArrayList;
    FirebaseFirestore db ;
    CollectionReference bookingsRef;
    RidesAdapter adapter;
    ProgressBar loading;

    private final OnSuccessListener<QuerySnapshot> getDriversSuccess = new OnSuccessListener<QuerySnapshot>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            if (!queryDocumentSnapshots.isEmpty()) {
                try {
                    loading.setVisibility(View.GONE);
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc : documents) {
                        BookingDetails bookingDetails = doc.toObject(BookingDetails.class, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE);
                        bookingDetailsArrayList.add(bookingDetails);
                    }
                    adapter.notifyDataSetChanged();
                } catch (IllegalStateException e) {
                    Toast.makeText(RidesActivity.this, "error occurred", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(RidesActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private final OnFailureListener getDriversFailure =  new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(RidesActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
        }
    };

    private void getBookings() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        bookingsRef
                .whereEqualTo("passengerId", user.getUid())
                .get()
                .addOnSuccessListener(RidesActivity.this, getDriversSuccess)
                .addOnFailureListener(RidesActivity.this, getDriversFailure);
    };

    private final AdapterView.OnItemClickListener driverItemClickListener =  new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(new Intent(RidesActivity.this, LocationActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_list);

        findViewById(R.id.rides_list_back_btn).setOnClickListener(v -> onBackPressed());

        RecyclerView listView = (RecyclerView) findViewById(R.id.rides_list);
        loading = (ProgressBar) findViewById(R.id.rides_progress_bar);

        db = FirebaseFirestore.getInstance();
        bookingsRef = db.collection("bookings");
        bookingDetailsArrayList = new ArrayList<>();
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listView.setHasFixedSize(true);
        adapter = new RidesAdapter(RidesActivity.this, bookingDetailsArrayList);
        listView.setAdapter(adapter);
        getBookings();
    }

}