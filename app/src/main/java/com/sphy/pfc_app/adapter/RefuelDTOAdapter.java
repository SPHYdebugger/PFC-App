package com.sphy.pfc_app.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.DTO.RefuelDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.view.refuels.RefuelDetailsView;
import com.sphy.pfc_app.view.refuels.RefuelListView;
import com.sphy.pfc_app.view.vehicles.VehicleDetailsView;

import java.util.List;

public class RefuelDTOAdapter extends RecyclerView.Adapter<RefuelDTOAdapter.RefuelHolder> {

    private List<RefuelDTO> refuels;

    public RefuelDTOAdapter(List<RefuelDTO> refuels) {
        this.refuels = refuels;
    }

    @NonNull
    @Override
    public RefuelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.refuel_item, parent, false);
        return new RefuelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RefuelHolder holder, int position) {


        holder.creationDate.setText(refuels.get(position).getCreationDate());
        holder.amount.setText(String.valueOf(refuels.get(position).getAmount()));
        holder.stationName.setText(refuels.get(position).getStationName());
        holder.fulled.setChecked(refuels.get(position).isFulled());


    }

    @Override
    public int getItemCount() {
        return refuels.size();
    }

    public static class RefuelHolder extends RecyclerView.ViewHolder {

        public TextView creationDate;
        public TextView amount;
        public TextView stationName;
        public Button detailsButton;
        public CheckBox fulled;


        public RefuelHolder(@NonNull View itemView) {
            super(itemView);

            creationDate = itemView.findViewById(R.id.creationDate);
            amount = itemView.findViewById(R.id.amount);
            stationName = itemView.findViewById(R.id.station);
            detailsButton = itemView.findViewById(R.id.detailsButton);
            fulled = itemView.findViewById(R.id.fulledCheck);

            detailsButton = itemView.findViewById(R.id.detailsButton);

        }


    }

}
