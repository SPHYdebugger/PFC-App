package com.sphy.pfc_app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.sphy.pfc_app.view.BaseActivity;
import com.sphy.pfc_app.view.vehicles.RegisterVehicleView;
import com.sphy.pfc_app.view.vehicles.SelectionVehicleListView;
import com.sphy.pfc_app.view.vehicles.VehicleListView;

public class MainMenu extends BaseActivity {


    private ImageButton menuButton;
    private ImageButton addRefuelButton;
    private ImageButton addVehicleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);

        addVehicleButton = findViewById(R.id.addVehicle);
        addRefuelButton = findViewById(R.id.addRefuel);
        menuButton = findViewById(R.id.menuButton);



        addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainMenu.this, RegisterVehicleView.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        addRefuelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainMenu.this, SelectionVehicleListView.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });




    }





}