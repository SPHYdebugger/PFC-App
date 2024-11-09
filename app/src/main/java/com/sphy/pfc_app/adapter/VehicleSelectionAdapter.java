package com.sphy.pfc_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.view.refuels.RegisterRefuelView;
import com.sphy.pfc_app.view.vehicles.SelectionVehicleListView;

import java.util.List;

public class VehicleSelectionAdapter extends RecyclerView.Adapter<VehicleSelectionAdapter.VehicleViewHolder> {

    private List<VehicleDTO> vehicles;


    public VehicleSelectionAdapter(List<VehicleDTO> vehicles) {

        this.vehicles = vehicles;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_item_selection, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        VehicleDTO vehicle = vehicles.get(position);


        holder.licenseTextView.setText(vehicle.getLicensePlate());
        holder.kmsTextView.setText(String.valueOf(vehicle.getKmActual()));


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), RegisterRefuelView.class);
            intent.putExtra("vehicleId", vehicle.getId());
            intent.putExtra("vehicleLicense", vehicle.getLicensePlate());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder {
        public TextView licenseTextView;
        public TextView kmsTextView;

        public VehicleViewHolder(@NonNull View view) {
            super(view);
            licenseTextView = view.findViewById(R.id.license);
            kmsTextView = view.findViewById(R.id.kms);
        }
    }
}

