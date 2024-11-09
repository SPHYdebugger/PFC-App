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
import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.api.StationApi;
import com.sphy.pfc_app.api.StationApiInterface;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.view.stations.StationDetailsView;
import com.sphy.pfc_app.view.vehicles.VehicleDetailsView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationDTOAdapter extends RecyclerView.Adapter<StationDTOAdapter.TaskHolder> {

    private List<StationDTO> stations;


    public StationDTOAdapter(List<StationDTO> stations) {this.stations = stations;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.station_item, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {


        holder.name.setText(stations.get(position).getName());




    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {


        public TextView name;

        public Button getDetailsButton;
        public Button deleteButton;
        public View parentView;

        public TaskHolder(@NonNull View view) {
            super(view);
            parentView = view;
            name = view.findViewById(R.id.name);

            deleteButton = view.findViewById(R.id.button2);
            getDetailsButton = view.findViewById(R.id.button);


            getDetailsButton.setOnClickListener(v -> goStationDetails(view));
            deleteButton.setOnClickListener(v -> deleteStation());


        }


        private void goStationDetails(View itemView) {
            Intent intent = new Intent(itemView.getContext(), StationDetailsView.class);
            StationDTO station = stations.get(getAdapterPosition());
            intent.putExtra("Id", station.getId());
            intent.putExtra("name", station.getName());
            itemView.getContext().startActivity(intent);
        }




        private void deleteStation() {

            AlertDialog.Builder builder = new AlertDialog.Builder(parentView.getContext());
            builder.setTitle("ALERTA, CONFIRMACIÓN");
            builder.setMessage("¿Está seguro de borrar esa estación de servicio de su lista?");

            //Boton confirmar
            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    hideStationConfirmed();
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


        private void hideStationConfirmed() {
            int currentPosition = getAdapterPosition();
            long stationId = stations.get(currentPosition).getId();
            String stationName = stations.get(currentPosition).getName();
            System.out.println("EstaciónDTO con id " + stationId + " tiene estado inicial del hide: " + stations.get(currentPosition).isHide());
            StationApiInterface api = StationApi.buildInstance();
            Call<StationDTO> stationToHideCall = api.getStationDTOById(stationId);
            stationToHideCall.enqueue(new Callback<StationDTO>() {
                @Override
                public void onResponse(Call<StationDTO> call, Response<StationDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        StationDTO stationDTOToHide = response.body();
                        Gson gson = new Gson();

                        Station stationTemp = new Station();
                        stationTemp.setName(stationDTOToHide.getName());
                        stationTemp.setAddress(stationDTOToHide.getAddress());
                        stationTemp.setSite(stationDTOToHide.getSite());
                        stationTemp.setProvince(stationDTOToHide.getProvince());
                        stationTemp.setGlpFuel(stationDTOToHide.isGlpFuel());
                        stationTemp.setHide(stationDTOToHide.isHide());

                        stationTemp.setHide(true);

                        Call<Station> hideStationCall = api.editStationById(stationId, stationTemp);
                        hideStationCall.enqueue(new Callback<Station>() {
                            @Override
                            public void onResponse(Call<Station> call, Response<Station> response) {
                                if (response.isSuccessful()) {

                                    //stations.get(currentPosition).setHide(true);

                                    stations.remove(currentPosition);
                                    notifyItemRemoved(currentPosition);
                                    notifyItemRangeChanged(currentPosition, stations.size());

                                } else {
                                    Log.e("hideStation", "Error al actualizar la estación: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<Station> call, Throwable t) {
                                Log.e("hideStation", "Error al conectar con el servidor: " + t.getMessage());
                            }
                        });
                    } else {
                        Log.e("hideStation", "Error al obtener la estación: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<StationDTO> call, Throwable t) {
                    Log.e("hideStation", "Error al conectar con el servidor para obtener la estación: " + t.getMessage());
                }
            });
        }

    }



}