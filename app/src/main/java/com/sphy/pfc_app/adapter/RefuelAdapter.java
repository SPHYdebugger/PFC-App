package com.sphy.pfc_app.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.R;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.view.refuels.RefuelDetailsView;

import java.util.List;

public class RefuelAdapter extends RecyclerView.Adapter<RefuelAdapter.RefuelHolder> {

    private List<Refuel> refuels;

    public RefuelAdapter(List<Refuel> refuels) {
        this.refuels = refuels;
    }

    @NonNull
    @Override
    public RefuelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.refuel_item, parent, false);
        return new RefuelHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RefuelHolder holder, int position) {
        Refuel refuel = refuels.get(position);


        String fuelText;
        if (refuel.getFuel() != null && !refuel.getFuel().isEmpty() && refuel.getSecondFuel() != null && !refuel.getSecondFuel().isEmpty()) {
            fuelText = String.format("%s / %s", refuel.getFuel(), refuel.getSecondFuel());
        } else if (refuel.getFuel() != null && !refuel.getFuel().isEmpty()) {
            fuelText = refuel.getFuel();
        } else if (refuel.getSecondFuel() != null && !refuel.getSecondFuel().isEmpty()) {
            fuelText = refuel.getSecondFuel();
        } else {
            fuelText = "";
        }
        holder.fuel.setText(fuelText);

        String priceText;
        if (refuel.getPrice() != 0 && refuel.getSecondPrice() != 0) {
            priceText = String.format("%.2f / %.2f", refuel.getPrice(), refuel.getSecondPrice());
        } else if (refuel.getPrice() != 0) {
            priceText = String.format("%.2f", refuel.getPrice());
        } else if (refuel.getSecondPrice() != 0) {
            priceText = String.format("%.2f", refuel.getSecondPrice());
        } else {
            priceText = "";
        }
        holder.price.setText(priceText);

        String amountText;
        if (refuel.getAmount() != 0 && refuel.getSecondAmount() != 0) {
            amountText = String.format("%.2f / %.2f", refuel.getAmount(), refuel.getSecondAmount());
        } else if (refuel.getAmount() != 0) {
            amountText = String.format("%.2f", refuel.getAmount());
        } else if (refuel.getSecondAmount() != 0) {
            amountText = String.format("%.2f", refuel.getSecondAmount());
        } else {
            amountText = "";
        }
        holder.amount.setText(amountText);

        holder.creationDate.setText(refuel.getCreationDate());
        holder.stationName.setText(refuel.getNameStation());
        if (refuel.isFulled()) {
            holder.fulledIcon.setImageResource(R.drawable.comprobado);
        }else {
            holder.fulledIcon.setImageResource(R.drawable.eliminar);
        }

        holder.detailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RefuelDetailsView.class);
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
        public TextView fuel;
        public TextView price;
        public Button detailsButton;
        public ImageView fulledIcon;


        public RefuelHolder(@NonNull View itemView) {
            super(itemView);

            creationDate = itemView.findViewById(R.id.creationDate);
            amount = itemView.findViewById(R.id.amount);
            stationName = itemView.findViewById(R.id.station);
            fuel = itemView.findViewById(R.id.fuel);
            price = itemView.findViewById(R.id.price);
            detailsButton = itemView.findViewById(R.id.detailsButton);
            fulledIcon = itemView.findViewById(R.id.fulledIcon);

        }
    }


}
