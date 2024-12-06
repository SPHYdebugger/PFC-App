package com.sphy.pfc_app;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.view.BaseActivity;
import com.sphy.pfc_app.view.stations.RegisterStationView;
import com.sphy.pfc_app.view.vehicles.RegisterVehicleView;
import com.sphy.pfc_app.view.vehicles.SelectionVehicleListView;
import com.sphy.pfc_app.view.vehicles.VehicleListView;
public class MainMenu extends BaseActivity {

    private ImageButton menuButton;
    private LinearLayout linearButton1;
    private LinearLayout linearButton2;
    private LinearLayout linearButton3;

    private TextView username;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        menuButton = findViewById(R.id.menuButton);
        linearButton1 = findViewById(R.id.linearButton1);
        linearButton2 = findViewById(R.id.linearButton2);
        linearButton3 = findViewById(R.id.linearButton3);
        username = findViewById(R.id.userNameTextView);

        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(user.toUpperCase());

        setupMenuButton(menuButton);

        linearButton1.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, SelectionVehicleListView.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        linearButton2.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, RegisterVehicleView.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        linearButton3.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, RegisterStationView.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }
}
