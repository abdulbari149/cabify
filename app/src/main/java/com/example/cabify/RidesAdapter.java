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


public class RidesAdapter extends ArrayAdapter<Captain>{
    public RidesAdapter(Context context, ArrayList<Captain> CaptainArrayList){

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
        TextView Durtime = convertView.findViewById(R.id.Durtime);
        TextView cosRS = convertView.findViewById(R.id.cosRS);
        TextView pick = convertView.findViewById(R.id.pick);
        TextView drop = convertView.findViewById(R.id.drop);


        imageView.setImageResource(captain.imageid);
        name.setText(captain.name);
        Pickup.setText(captain.pickup);
        Dropoff.setText(captain.dropoff);
        Duration.setText(captain.duration);
        Cost.setText(captain.cost);
        Durtime.setText(captain.Durtime);
        cosRS.setText(captain.cosRS);
        pick.setText(captain.pick);
        drop.setText(captain.drop);

        return convertView;
    }
}
