package com.sphy.pfc_app.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.DTO.RefuelDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.view.refuels.RefuelListView;

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
        RefuelDTO refuel = refuels.get(position);

        holder.creationDate.setText(refuel.getCreationDate());
        holder.amount.setText(String.valueOf(refuel.getAmount()));
        holder.stationName.setText(refuel.getStationName());

        // Manejo de detalles en el botón
        holder.detailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RefuelListView.class);
            intent.putExtra("refuelId", refuel.getId());
            v.getContext().startActivity(intent);
        });
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

        public RefuelHolder(@NonNull View itemView) {
            super(itemView);

            creationDate = itemView.findViewById(R.id.creationDate);
            amount = itemView.findViewById(R.id.amount);
            stationName = itemView.findViewById(R.id.station);
            detailsButton = itemView.findViewById(R.id.button);
        }
    }
}
