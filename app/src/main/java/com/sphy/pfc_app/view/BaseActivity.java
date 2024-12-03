package com.sphy.pfc_app.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.sphy.pfc_app.MainActivity;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.adapter.MenuAdapter;
import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.view.refuels.RefuelDetailsGrafByVehicleView;
import com.sphy.pfc_app.view.stations.StationListView;
import com.sphy.pfc_app.view.users.UserDetailsView;
import com.sphy.pfc_app.view.vehicles.VehicleConsumDetailsView;
import com.sphy.pfc_app.view.vehicles.VehicleListView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupMenuButton(View menuButton) {
        menuButton.setOnClickListener(v -> showMenuDialog(menuButton));
    }

    private void showMenuDialog(View menuButton) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_menu);


        List<String> options = Arrays.asList(getString(R.string.vehicles), getString(R.string.stations), getString(R.string.history),getString(R.string.change_language), "", getString(R.string.exit));
        ListView listView = dialog.findViewById(R.id.listViewOptions);
        MenuAdapter adapter = new MenuAdapter(this, options);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options.get(position);

                dialog.dismiss(); // Cierra el diálogo después de seleccionar

                if (getString(R.string.vehicles).equals(selectedOption)) {

                    Intent intent = new Intent(BaseActivity.this, VehicleListView.class);
                    startActivity(intent);
                } else if (getString(R.string.stations).equals(selectedOption)) {

                    Intent intent = new Intent(BaseActivity.this, StationListView.class);
                    startActivity(intent);
                } else if (getString(R.string.history).equals(selectedOption)) {

                    Intent intent = new Intent(BaseActivity.this, UserDetailsView.class);
                    startActivity(intent);
                } else if (getString(R.string.change_language).equals(selectedOption)) {

                    toggleLanguage();
                } else if (getString(R.string.exit).equals(selectedOption)) {
                    //borrar el token
                    SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(BaseActivity.this);
                    sharedPreferencesManager.clear();

                    // Redirigir a MainActivity
                    Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                    //borrar toda información deautentifacacion
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(BaseActivity.this, "Opción seleccionada: " + selectedOption, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Calcular las coordenadas del botón
        int[] location = new int[2];
        menuButton.getLocationOnScreen(location);
        int x = location[0]; // Coordenada X
        int y = location[1] + menuButton.getHeight(); // Coordenada Y, justo debajo del botón

        // Ajustar la posición del diálogo
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.x = x; // Coordenada X
        layoutParams.y = y; // Coordenada Y
        layoutParams.gravity = Gravity.TOP | Gravity.START; // Alineación en la esquina superior izquierda
        dialog.getWindow().setAttributes(layoutParams);

        // Añadir animación de entrada
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; // Establece animaciones en el diálogo

        dialog.show(); // Muestra el diálogo

        // Animación de entrada
        dialog.getWindow().getDecorView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_up));

        // Añadir un listener para animar la salida
        dialog.setOnDismissListener(dialogInterface -> {
            // Animación de salida
            dialog.getWindow().getDecorView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_down));
        });
    }

    private void toggleLanguage() {
        Locale currentLocale = getResources().getConfiguration().locale;
        Locale newLocale;
        if (currentLocale.getLanguage().equals("es")) {
            newLocale = Locale.ENGLISH;
        } else {
            newLocale = new Locale("es");
        }
        changeLanguage(newLocale);
        recreate();
    }

    private void changeLanguage(Locale locale) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

}
