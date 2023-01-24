package com.example.cabify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class VehicleActivity extends AppCompatActivity {
    String captainlist [] = {"Captain1","Captain2","Captain3","Captain4","Captain5"};
    int image [] = {R.drawable.ic_baseline_person_24};
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(VehicleActivity.this, LocationActivity.class));
            }
        });
    }
}