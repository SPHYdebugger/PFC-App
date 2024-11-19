package com.sphy.pfc_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sphy.pfc_app.login.LoginActivity;
import com.sphy.pfc_app.view.users.UserRegisterView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btnAcceder);
        button2 = findViewById(R.id.btnRegistro);

        System.out.println("hola");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar la segunda actividad
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar la segunda actividad
                Intent intent = new Intent(MainActivity.this, UserRegisterView.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}