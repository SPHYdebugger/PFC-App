package com.sphy.pfc_app.view.vehicles;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sphy.pfc_app.R;
import com.sphy.pfc_app.view.BaseActivity;

public class PostVehicles extends BaseActivity {

    private ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);

        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);

        /*public void createVehicle(View view) {
            EditText etName = findViewById(R.id.client_firstName);
            EditText etLastname = findViewById(R.id.client_lastName);
            EditText etDni = findViewById(R.id.client_dni);
            EditText etCity = findViewById(R.id.cliente_city);
            CheckBox checkVip = findViewById(R.id.client_vip);

            String name = etName.getText().toString();
            String lastname = etLastname.getText().toString();
            String dni = etDni.getText().toString();
            String city = etCity.getText().toString();
            boolean vip = checkVip.isChecked();

            Client client = new Client(name, lastname, dni, city, vip);
            presenter.insertClient(client);
        }

        @Override
        public void showInsertSuccessMessage() {
            Toast.makeText(this,R.string.client_successful_added, Toast.LENGTH_LONG).show();
        }

        @Override
        public void showInsertErrorMessage() {
            Toast.makeText(this, "Error al insertar el cliente", Toast.LENGTH_LONG).show();
        }

        @Override
        public void clearFields() {
            EditText etName = findViewById(R.id.client_firstName);
            EditText etLastname = findViewById(R.id.client_lastName);
            EditText etDni = findViewById(R.id.client_dni);
            EditText etCity = findViewById(R.id.cliente_city);


            etName.setText("");
            etLastname.setText("");
            etDni.setText("");
            etCity.setText("");

            etName.requestFocus();
        }*/

    }
}
