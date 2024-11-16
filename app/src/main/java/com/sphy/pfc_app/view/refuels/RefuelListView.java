package com.sphy.pfc_app.view.refuels;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.R;
import com.sphy.pfc_app.adapter.RefuelAdapter;
import com.sphy.pfc_app.contract.refuels.RefuelListContract;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.presenter.Refuels.RefuelListPresenter;
import com.sphy.pfc_app.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class RefuelListView extends BaseActivity implements RefuelListContract.View {

    private List<Refuel> refuels;
    private RefuelAdapter adapter;
    private RefuelListPresenter presenter;

    private ImageButton menuButton;
    private Button buttonGraf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_refuels);

        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);
        buttonGraf = findViewById(R.id.button_graf);



        presenter = new RefuelListPresenter(this);

        refuels = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.refuels_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RefuelAdapter(refuels);
        recyclerView.setAdapter(adapter);


        buttonGraf.setOnClickListener(v -> {
            String identifier = getIntent().getStringExtra("identifier");
            System.out.println("El identifier que se manda a gráfica es: " + identifier);

            String regexForStation = "^[a-zA-Z\\s]+\\d{0,2}$";
            String regexForVehicle = "^[a-zA-Z0-9]+$";

            if (identifier.matches(regexForVehicle) && !identifier.matches(regexForStation)) {
                Intent intent = new Intent(RefuelListView.this, RefuelDetailsGrafByVehicleView.class);
                intent.putExtra("identifier", identifier);
                startActivity(intent);
            } else if (identifier.matches(regexForStation)) {
                Intent intent = new Intent(RefuelListView.this, RefuelDetailsGrafByStationView.class);
                intent.putExtra("identifier", identifier);
                startActivity(intent);
            }
        });


        String identifier = getIntent().getStringExtra("identifier");
        System.out.println("El identifier es: " + identifier);
        presenter.findRefuelByIdentifier(identifier);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String identifier = getIntent().getStringExtra("identifier");
        System.out.println("El identifier es: " + identifier);
        presenter.findRefuelByIdentifier(identifier);
    }

    @Override
    public void listRefuels(List<Refuel> refuels) {
        this.refuels.clear();
        this.refuels.addAll(refuels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}
