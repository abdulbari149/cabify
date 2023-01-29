package com.abdulbari149.driverx;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.RidesViewHolder> {


    Context context;
    ArrayList<BookingDetails> bookingDetailsArrayList;


    public RidesAdapter(Context context, ArrayList<BookingDetails> bookingDetailsArrayList) {
        this.context = context;
        this.bookingDetailsArrayList = bookingDetailsArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull RidesViewHolder holder, int position) {
        BookingDetails bookingDetails= this.bookingDetailsArrayList.get(position);
        holder.bind(bookingDetails);
    }

    @NonNull
    @Override
    public RidesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.activity_rides_item, parent, false);
        return new RidesViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return this.bookingDetailsArrayList.size();
    }

    public static class RidesViewHolder extends RecyclerView.ViewHolder {
        TextView pickupTextView;
        TextView dropOffTextView;
        TextView distanceTextView;
        TextView costTextView;
        TextView driverNameTextView;

        public RidesViewHolder(@NonNull View itemView){
            super(itemView);
            pickupTextView = (TextView) itemView.findViewById(R.id.pick_text);
            dropOffTextView = (TextView) itemView.findViewById(R.id.dropoff_text);
//            distanceTextView = (TextView) itemView.findViewById(R.id.distance_text);
            costTextView = (TextView) itemView.findViewById(R.id.cost_text);
            driverNameTextView = (TextView) itemView.findViewById(R.id.driver_name_text);
        }

        public void bind(BookingDetails model) {
            pickupTextView.setText(model.getPickup());
            dropOffTextView.setText(model.getDropoff());
//            distanceTextView.setText(Double.toString(model.getDistance()));
            costTextView.setText("Cost : Rs." + Double.toString(model.getPrice()));
            driverNameTextView.setText(model.getDriverName());
        }

    }
}
