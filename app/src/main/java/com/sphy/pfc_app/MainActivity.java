package com.sphy.pfc_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btnAcceder);

        System.out.println("hola");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar la segunda actividad
                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}