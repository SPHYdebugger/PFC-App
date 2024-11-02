package com.sphy.pfc_app.view.refuels;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
    String licensePlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_refuels);

        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);

        presenter = new RefuelListPresenter(this);

        refuels = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.refuels_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RefuelAdapter(refuels);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        String identifier = intent.getStringExtra("licensePlate");
        System.out.println("esto es " + identifier);
        presenter.findRefuelByIdentifier(identifier);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String identifier = intent.getStringExtra("licensePlate");
        System.out.println("esto es " + identifier);
        presenter.findRefuelByIdentifier(identifier);
    }

    @Override
    public void listVehicleRefuels(List<Refuel> refuels) {
        this.refuels.clear();
        this.refuels.addAll(refuels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
