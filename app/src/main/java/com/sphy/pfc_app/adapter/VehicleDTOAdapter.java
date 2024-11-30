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

import com.google.gson.Gson;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.domain.Vehicle;
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
        holder.brand.setText(vehicles.get(position).getBrand());
        holder.model.setText(vehicles.get(position).getModel());
        holder.kms.setText(String.valueOf(vehicles.get(position).getKmActual()));
        holder.consum.setText(String.valueOf(vehicles.get(position).getMedConsumption()));




    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {


        public TextView license;
        public TextView brand;
        public TextView model;
        public TextView kms;
        public TextView consum;


        public Button getDetailsButton;
        public Button deleteButton;
        public View parentView;

        public TaskHolder(@NonNull View view) {
            super(view);
            parentView = view;
            license = view.findViewById(R.id.license);
            brand = view.findViewById(R.id.brand);
            model = view.findViewById(R.id.model);
            kms = view.findViewById(R.id.kms);
            consum = view.findViewById(R.id.medConsum);

            deleteButton = view.findViewById(R.id.button2);
            getDetailsButton = view.findViewById(R.id.detailsButton);


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
            String vehicleLicense = vehicles.get(currentPosition).getLicensePlate();
            System.out.println("VehiculoDTO con id " + vehicleId + " tiene estado inicial del hide: " + vehicles.get(currentPosition).isHide());
            VehicleApiInterface api = VehicleApi.buildInstance(parentView.getContext());
            Call<Vehicle> vehicleToHideCall = api.getVehicleById(vehicleId);
            vehicleToHideCall.enqueue(new Callback<Vehicle>() {
                @Override
                public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Vehicle vehicleToHide = response.body();
                        Gson gson = new Gson();

                        Vehicle vehicleTemp = new Vehicle();
                        vehicleTemp.setLicensePlate(vehicleToHide.getLicensePlate());
                        vehicleTemp.setBrand(vehicleToHide.getBrand());
                        vehicleTemp.setModel(vehicleToHide.getModel());
                        vehicleTemp.setFuel1(vehicleToHide.getFuel1());
                        vehicleTemp.setFuel2(vehicleToHide.getFuel2());
                        vehicleTemp.setKmActual(vehicleToHide.getKmActual());
                        vehicleTemp.setMedConsumption(vehicleToHide.getMedConsumption());
                        vehicleTemp.setRegistrationDate(vehicleToHide.getRegistrationDate());
                        vehicleTemp.setHide(vehicleToHide.isHide());

                        vehicleToHide.setHide(true);

                        Call<Vehicle> hideVehicleCall = api.editVehicleByLicense(vehicleLicense, vehicleToHide);
                        hideVehicleCall.enqueue(new Callback<Vehicle>() {
                            @Override
                            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                                if (response.isSuccessful()) {

                                    //vehicles.get(currentPosition).setHide(true);

                                    vehicles.remove(currentPosition);
                                    notifyItemRemoved(currentPosition);
                                    notifyItemRangeChanged(currentPosition, vehicles.size());

                                } else {
                                    Log.e("hideVehicle", "Error al actualizar el vehículo: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<Vehicle> call, Throwable t) {
                                Log.e("hideVehicle", "Error al conectar con el servidor: " + t.getMessage());
                            }
                        });
                    } else {
                        Log.e("hideVehicle", "Error al obtener el vehículo: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Vehicle> call, Throwable t) {
                    Log.e("hideVehicle", "Error al conectar con el servidor para obtener el vehículo: " + t.getMessage());
                }
            });
        }

    }



}