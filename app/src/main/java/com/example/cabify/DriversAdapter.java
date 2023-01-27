
package com.example.cabify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DriversAdapter extends BaseAdapter {

    Context context;
    List<Driver> driversList;
    LayoutInflater inflater;
    int imageCaptain = R.drawable.ic_baseline_person_24;

    public DriversAdapter(Context ctx, List<Driver> driversList){
        this.context = ctx;
        this.driversList = driversList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return driversList.size();
    }


    @Override
    public Driver getItem(int position) {
        return driversList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int position, View view, ViewGroup parent) {
//        View listItemView = inflater.inflate(R.layout.activity_custom_list_view,null);
//        ((TextView) listItemView.findViewById(R.id.text)).setText(driversList.get(position).getName());
//        ((ImageView) listItemView.findViewById(R.id.image)).setImageResource(imageCaptain);
//        ((TextView) listItemView.findViewById(R.id.status)).setText(
//                driversList.get(position).isAvailable() ? "Available" : "Not Available"
//        );
//        return listItemView;
        return view;
    }
}
