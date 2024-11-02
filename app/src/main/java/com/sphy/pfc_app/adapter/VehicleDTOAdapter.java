package com.sphy.pfc_app.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.view.refuels.RefuelListView;
import com.sphy.pfc_app.view.vehicles.VehicleDetailsView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleDTOAdapter extends RecyclerView.Adapter<VehicleDTOAdapter.TaskHolder> {

    private List<VehicleDTO> vehicles;


    public VehicleDTOAdapter(List<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_item, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {


        holder.license.setText(vehicles.get(position).getLicensePlate());




    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {


        public TextView license;

        public Button getDetailsButton;
        public Button deleteButton;
        public View parentView;

        public TaskHolder(@NonNull View view) {
            super(view);
            parentView = view;
            license = view.findViewById(R.id.license);

            deleteButton = view.findViewById(R.id.button2);
            getDetailsButton = view.findViewById(R.id.button);


            getDetailsButton.setOnClickListener(v -> goVehicleDetails(view));
            deleteButton.setOnClickListener(v -> deleteVehicle());


        }


        private void goVehicleDetails(View itemView) {
            Intent intent = new Intent(itemView.getContext(), VehicleDetailsView.class);
            VehicleDTO vehicle = vehicles.get(getAdapterPosition());
            intent.putExtra("id", vehicle.getId());
            intent.putExtra("licensePlate", vehicle.getLicensePlate());
            itemView.getContext().startActivity(intent);
        }




        private void deleteVehicle() {

            AlertDialog.Builder builder = new AlertDialog.Builder(parentView.getContext());
            builder.setTitle("ALERTA, CONFIRMACIÓN");
            builder.setMessage("¿Está seguro de borrar ese vehículo de su lista?");

            //Boton confirmar
            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    hideVehicleConfirmed();
                }
            });

            //boton cancelar
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            //Mostrar el aviso
            AlertDialog dialog = builder.create();
            dialog.show();
        }


        private void hideVehicleConfirmed() {
            int currentPosition = getAdapterPosition();
            long vehicleId = vehicles.get(currentPosition).getId();

            VehicleApiInterface api = VehicleApi.buildInstance();
            Call<Void> hideVehicleCall = api.hideVehicleById(vehicleId);
            hideVehicleCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    vehicles.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                    notifyItemRangeChanged(currentPosition, vehicles.size());

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("deleteClient", "Error al conectar con el servidor: " + t.getMessage());

                }
            });
        }
    }



}