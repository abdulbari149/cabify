package com.example.cabify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cabify.databinding.ActivityMainBinding;
import com.example.cabify.databinding.ActivityRidesActivityBinding;


import java.util.ArrayList;

public class RidesActivity extends AppCompatActivity {


    ActivityRidesActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRidesActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        int[] imageid = {R.drawable.cap1, R.drawable.cap1, R.drawable.cap1, R.drawable.cap1, R.drawable.cap1,
                R.drawable.cap1};
        String[] name = {"Usaid", "Rafay", "Maaz", "Wali", "Mustafa", "Faizan"};
        String[] pickup = {"Gulshan", "Ancholi?", "Nazimabad?",
                "Saddar", "Luckyone", "Nueplex"};
        String[] dropoff = {"Saddar", "Luckyone", "Nueplex", "Ancholi?", "Nazimabad?",
                "abc"};
        String[] duration = {"1hr", "10min", "7min", "67min", "2hr",
                "6hr"};
        String[] cost = {"500", "400", "300", "200", "144", "8888"};


        ArrayList<Captain> captainArrayList = new ArrayList<>();

        for (int i = 0; i < imageid.length; i++) {

            Captain captain = new Captain(name[i], pickup[i], dropoff[i], duration[i], cost[i],imageid[i]);
            captainArrayList.add(captain);

        }


        RidesActivityAdapter ridesAdapter = new RidesAdapter(RidesActivity.this, captainArrayList);
        binding.List.setAdapter(ridesAdapter);
    }}
//
//        binding.
//        binding.listview.setClickable(true);
//        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent i = new Intent(MainActivity.this,UserActivity.class);
//                i.putExtra("name",name[position]);
//                i.putExtra("phone",phoneNo[position]);
//                i.putExtra("imageid",imageId[position]);
//                startActivity(i);
//
//            }
//        });
//
//    }
//}