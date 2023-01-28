package com.abdulbari149.driverx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdulbari149.driverx.model.Driver;

import java.util.ArrayList;

public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.DriversViewHolder> {
    Context context;
    ArrayList<Driver> drivers;
    private final OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(Driver item);
    }

    public DriversAdapter(Context context, ArrayList<Driver> drivers, OnItemClickListener listener) {
        this.context = context;
        this.drivers = drivers;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull DriversViewHolder holder, int position) {
        Driver driver = this.drivers.get(position);
        holder.bind(driver, listener);
    }

    @NonNull
    @Override
    public DriversAdapter.DriversViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.activity_driver_item, parent, false);
        return new DriversViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return this.drivers.size();
    }

    public class DriversViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView statusView;

        public DriversViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.driver_image);
            nameView = (TextView) itemView.findViewById(R.id.driver_name);
            statusView = (TextView) itemView.findViewById(R.id.driver_status);
        }

        public void bind(Driver model,  final OnItemClickListener listener) {
            nameView.setText(model.getName());
            statusView.setText(model.isAvailable() ? "Available" : "Not Available");
            imageView.setImageResource(R.drawable.ic_baseline_person_24);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(model);
                }
            });
            //        try {
//            InputStream in = model.getImage().openConnection().getInputStream();
//            imageView.setImageBitmap(BitmapFactory.decodeStream(in));
//        } catch (IOException e) {
            //        }
        }

    }
}
