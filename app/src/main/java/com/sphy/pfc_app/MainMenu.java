package com.sphy.pfc_app;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sphy.pfc_app.adapter.MenuAdapter;
import com.sphy.pfc_app.view.BaseActivity;
import com.sphy.pfc_app.view.vehicles.PostVehicles;
import com.sphy.pfc_app.view.vehicles.VehicleListView;

import java.util.Arrays;
import java.util.List;

public class MainMenu extends BaseActivity {


    private ImageButton menuButton;
    private ImageButton listVehicleButton;
    private ImageButton addVehicleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);

        addVehicleButton = findViewById(R.id.addVehicle);
        listVehicleButton = findViewById(R.id.listVehicles);
        menuButton = findViewById(R.id.menuButton);



        addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar la actividad
                Intent intent = new Intent(MainMenu.this, PostVehicles.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        listVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar la actividad
                Intent intent = new Intent(MainMenu.this, VehicleListView.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });




    }





}