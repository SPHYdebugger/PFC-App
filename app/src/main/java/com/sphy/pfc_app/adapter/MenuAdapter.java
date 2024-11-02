package com.sphy.pfc_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<String> {
    public MenuAdapter(Context context, List<String> options) {
        super(context, 0, options);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtén el dato para esta posición
        String option = getItem(position);

        // Infla la vista si es necesario
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        // Configura la vista
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(option);
        textView.setTextColor(getContext().getResources().getColor(android.R.color.white));
        textView.setTextSize(20);

        return convertView;
    }
}
