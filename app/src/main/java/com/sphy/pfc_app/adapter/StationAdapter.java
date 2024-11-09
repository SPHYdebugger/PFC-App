package com.sphy.pfc_app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sphy.pfc_app.DTO.StationDTO;

import java.util.List;

public class StationAdapter extends ArrayAdapter<StationDTO> {

    private Context context;
    private List<StationDTO> stations;

    public StationAdapter(Context context, List<StationDTO> stations) {
        super(context, android.R.layout.simple_spinner_item, stations);
        this.context = context;
        this.stations = stations;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView textView = view.findViewById(android.R.id.text1);

        textView.setText(stations.get(position).getName());
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = view.findViewById(android.R.id.text1);

        textView.setText(stations.get(position).getName());
        return view;
    }
}
