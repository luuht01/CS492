package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tutlane on 23-08-2017.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList<Item> listData;
    private LayoutInflater layoutInflater;
    public CustomListAdapter(Context aContext, ArrayList<Item> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.description = (TextView) v.findViewById(R.id.description);
            holder.productNumber = (TextView) v.findViewById(R.id.productNumber);
            holder.price = (TextView) v.findViewById(R.id.price);
            holder.website= (TextView) v.findViewById(R.id.website);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.description.setText(listData.get(position).getDescription());
        holder.productNumber.setText(listData.get(position).getProductNumber());
        holder.price.setText(listData.get(position).getPriceString());
        holder.website.setText(listData.get(position).getWebsiteUrl());
        return v;
    }
    static class ViewHolder {
        TextView description;
        TextView productNumber;
        TextView price;
        TextView website;
    }
}