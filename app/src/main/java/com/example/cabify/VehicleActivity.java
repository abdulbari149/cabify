package com.example.cabify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class VehicleActivity extends AppCompatActivity {
    String captainlist [] = {"Captain1","Captain2","Captain3","Captain4","Captain5"};
    int image [] = {R.drawable.person};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        Intent optionsIntent = getIntent();

        String vehicle = optionsIntent.getStringExtra("vehicle_type");

        listView = (ListView) findViewById(R.id.vehicle_list);
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(),captainlist,image);
        listView.setAdapter(customBaseAdapter);
    }
}