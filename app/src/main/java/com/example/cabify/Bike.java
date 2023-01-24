package com.example.cabify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class Bike extends AppCompatActivity {
    String captainlist [] = {"Captain1","Captain2","Captain3","Captain4","Captain5"};
    int image [] = {R.drawable.ic_baseline_person_24};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);
        listView = (ListView) findViewById(R.id.bike_list);
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(),captainlist,image);
        listView.setAdapter(customBaseAdapter);

    }
}