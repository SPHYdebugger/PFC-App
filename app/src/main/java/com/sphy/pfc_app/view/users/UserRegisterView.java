package com.sphy.pfc_app.view.users;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sphy.pfc_app.DTO.UserDTO;
import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.contract.user.UserRegisterContract;
import com.sphy.pfc_app.login.LoginActivity;
import com.sphy.pfc_app.presenter.user.UserRegisterPresenter;

public class UserRegisterView extends AppCompatActivity implements UserRegisterContract.View {

    private ProgressBar progressBar;
    private Button registerButton;
    private EditText usernameEditText, emailEditText, passwordEditText, nameEditText, surnameEditText;
    private UserRegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializamos los elementos de la vista
        progressBar = findViewById(R.id.progressBar);
        registerButton = findViewById(R.id.register_button);
        usernameEditText = findViewById(R.id.username);
        nameEditText = findViewById(R.id.nome);
        surnameEditText = findViewById(R.id.surname);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);


        presenter = new UserRegisterPresenter(this, this);

        // Configuración para el botón de registro
        registerButton.setOnClickListener(v -> {
            // Recoger los datos del formulario
            String username = usernameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String surname = surnameEditText.getText().toString();



                UserDTO userDTO = new UserDTO(username, password, name, surname, email);
                // Iniciar el registro
                presenter.registerUser(userDTO);

        });
    }

    @Override
    public void showLoading(boolean isLoading) {
        // Mostrar u ocultar el ProgressBar
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);  // Mostrar el ProgressBar
        } else {
            progressBar.setVisibility(View.GONE);    // Ocultar el ProgressBar
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void goLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

