package com.example.cabify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class RiderListViewAdapter extends ArrayAdapter<Captain>{
    public RiderListViewAdapter(Context context, ArrayList<Captain> CaptainArrayList){

        super(context,R.layout.list_item,CaptainArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Captain captain = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        TextView name = convertView.findViewById(R.id.name);
        TextView Pickup= convertView.findViewById(R.id.pickup);
        TextView Dropoff = convertView.findViewById(R.id.dropoff);
        TextView Cost = convertView.findViewById(R.id.cost);
        TextView Duration = convertView.findViewById(R.id.duration);


        imageView.setImageResource(captain.imageid);
        name.setText(captain.name);
        Pickup.setText(captain.pickup);
        Dropoff.setText(captain.dropoff);
        Duration.setText(captain.duration);
        Cost.setText(captain.cost);



        return convertView;
    }
}
