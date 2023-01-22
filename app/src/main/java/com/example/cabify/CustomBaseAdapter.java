package com.example.cabify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cabify.R;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String captainlist[];
    int image[];
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, String [] captainlist , int [] image){
        this.context = ctx;
        this.captainlist = captainlist;
        this.image = image;
        inflater = LayoutInflater.from(ctx);

    }

    @Override
    public int getCount() {
        return captainlist.length;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_custom_list_view,null);
        TextView txtview = (TextView) convertView.findViewById(R.id.text);
        ImageView imgview = (ImageView) convertView.findViewById(R.id.image);
        txtview.setText(captainlist[position]);
        imgview.setImageResource(image[position]);
        return convertView;
    }
}
