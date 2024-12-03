package com.sphy.pfc_app.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.api.RefuelApi;
import com.sphy.pfc_app.api.RefuelApiInterface;
import com.sphy.pfc_app.api.StationApi;
import com.sphy.pfc_app.api.StationApiInterface;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.view.stations.StationDetailsView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationDTOAdapter extends RecyclerView.Adapter<StationDTOAdapter.TaskHolder> {

    private List<StationDTO> stations;

    public StationDTOAdapter(List<StationDTO> stations) {
        this.stations = stations;
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
        holder.location.setText(stations.get(position).getSite());

        // Llamada a getRefuelsForStation pasando el contexto de holder
        getRefuelsForStation(stations.get(position).getName(), holder.itemView.getContext(), new RefuelCountCallback() {
            @Override
            public void onSuccess(int count) {
                holder.refuels.setText(String.valueOf(count)); // Muestra el número de repostajes
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Manejo de error, si es necesario
                Log.e("getRefuels", "Error al obtener los repostajes.");
                holder.refuels.setText("Error");
            }
        });

        if (stations.get(position).isGlpFuel()) {
            holder.icon.setImageResource(R.drawable.comprobado);
        } else {
            holder.icon.setImageResource(R.drawable.eliminar);
        }
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView location;
        public TextView refuels;
        public ImageView icon;

        public Button getDetailsButton;
        public Button deleteButton;

        public TaskHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.name);
            location = view.findViewById(R.id.location);
            refuels = view.findViewById(R.id.refuels);
            icon = view.findViewById(R.id.imageView2);

            deleteButton = view.findViewById(R.id.button2);
            getDetailsButton = view.findViewById(R.id.detailsButton);

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
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("ALERTA, CONFIRMACIÓN");
            builder.setMessage("¿Está seguro de borrar esa estación de servicio de su lista?");

            builder.setPositiveButton("SI", (dialog, which) -> hideStationConfirmed());
            builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        private void hideStationConfirmed() {
            int currentPosition = getAdapterPosition();
            long stationId = stations.get(currentPosition).getId();
            String stationName = stations.get(currentPosition).getName();
            System.out.println("EstaciónDTO con id " + stationId + " tiene estado inicial del hide: " + stations.get(currentPosition).isHide());

            StationApiInterface api = StationApi.buildInstance(itemView.getContext());
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
                                    stations.remove(currentPosition);
                                    notifyItemRemoved(currentPosition);
                                    notifyItemRangeChanged(currentPosition, stations.size());
                                    notifyDataSetChanged();
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

    // Método actualizado con Context como parámetro
    public void getRefuelsForStation(String stationIdentifier, Context context, RefuelCountCallback callback) {
        RefuelApiInterface api = RefuelApi.buildInstance(context); // Usamos el contexto pasado
        Call<List<Refuel>> getRefuelsCall = api.findRefuelByIdentifier(stationIdentifier);

        getRefuelsCall.enqueue(new Callback<List<Refuel>>() {
            @Override
            public void onResponse(Call<List<Refuel>> call, Response<List<Refuel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Refuel> refuels = response.body();
                    callback.onSuccess(refuels.size()); // Devuelve el tamaño de la lista
                } else {
                    Log.e("getRefuelsForStation", "Error al obtener repostajes: " + response.message());
                    callback.onFailure(new Exception("Error al obtener repostajes."));
                }
            }

            @Override
            public void onFailure(Call<List<Refuel>> call, Throwable t) {
                Log.e("getRefuelsForStation", "Error al conectar con el servidor: " + t.getMessage());
                callback.onFailure(t);
            }
        });
    }

    // Interfaz para recibir la cantidad de repostajes
    public interface RefuelCountCallback {
        void onSuccess(int count);
        void onFailure(Throwable throwable);
    }
}
