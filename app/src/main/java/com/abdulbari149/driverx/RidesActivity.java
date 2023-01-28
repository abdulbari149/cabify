package com.abdulbari149.driverx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

//import com.abdulbari149.driverx.databinding.ActivityMainBinding;
//import com.abdulbari149.driverx.databinding.ActivityRidesBinding;


import java.util.ArrayList;

public class RidesActivity extends AppCompatActivity {


//    ActivityRidesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityRidesBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());


        int[] imageid = {R.drawable.cap1, R.drawable.cap1, R.drawable.cap1, R.drawable.cap1, R.drawable.cap1,
                R.drawable.cap1,  R.drawable.cap1,  R.drawable.cap1};
        String[] name = {"Usaid", "Rafay", "Maaz", "Wali", "Mustafa", "Faizan","Bari","Ammar"};
        String[] pickup = {"Gulshan", "Ancholi", "Nazimabad ",
                "Saddar", "Luckyone", "Nueplex","Clifton","DHA"};
        String[] dropoff = {"Saddar", "Luckyone", "Nueplex", "Ancholi", "Nazimabad ",
                "abc","Nazimabad ","5star"};
        String[] Durtime = {"1hr", "10min", "7min", "67min", "2hr",
                "6hr","1.5hr","30min"};
        String [] duration = {"Duration:"};
        String[] cosRS = {"500rs", "400rs", "300rs", "200rs", "144rs", "8888rs","2000rs","550rs"};
        String [] cost = {"Cost:"};
        String [] pick = {"(Pick Up)"};
        String[] drop = {"(Drop Off)"};


        ArrayList<Captain> captainArrayList = new ArrayList<>();

        for (int i = 0; i < imageid.length; i++) {

            Captain captain = new Captain(name[i], pickup[i], dropoff[i], duration[0], cost[0],imageid[i],Durtime[i],cosRS[i],pick[0],drop[0]);
            captainArrayList.add(captain);

        }


        RidesAdapter ridesAdapter = new RidesAdapter(RidesActivity.this, captainArrayList);
//        binding.List.setAdapter(ridesAdapter);
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