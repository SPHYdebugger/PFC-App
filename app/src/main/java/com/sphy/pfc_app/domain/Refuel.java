package com.sphy.pfc_app.domain;



import java.time.LocalDate;
public class Refuel {




    private long id;
    private String nameStation;
    private String nameVehicle;
    private String fuel;
    private float amount;
    private float price;
    private int kmTotal;
    private int kmTraveled;
    private boolean fulled;
    private String creationDate;
    private float refuelConsumption;
    private float refueledLiters;
    private float medConsumption;


    private long vehicleId;
    private long stationId;


    public Refuel() {}

    public Refuel(long id, String nameStation, String nameVehicle, String fuel, float amount, float price, int kmTotal,
                  int kmTraveled, boolean fulled, String creationDate, float refuelConsumption, float refueledLiters,
                  float medConsumption, long vehicleId, long stationId) {
        this.id = id;
        this.nameStation = nameStation;
        this.nameVehicle = nameVehicle;
        this.fuel = fuel;
        this.amount = amount;
        this.price = price;
        this.kmTotal = kmTotal;
        this.kmTraveled = kmTraveled;
        this.fulled = fulled;
        this.creationDate = creationDate;
        this.refuelConsumption = refuelConsumption;
        this.refueledLiters = refueledLiters;
        this.medConsumption = medConsumption;
        this.vehicleId = vehicleId;
        this.stationId = stationId;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameStation() {
        return nameStation;
    }

    public void setNameStation(String nameStation) {
        this.nameStation = nameStation;
    }

    public String getNameVehicle() {
        return nameVehicle;
    }

    public void setNameVehicle(String nameVehicle) {
        this.nameVehicle = nameVehicle;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getKmTotal() {
        return kmTotal;
    }

    public void setKmTotal(int kmTotal) {
        this.kmTotal = kmTotal;
    }

    public int getKmTraveled() {
        return kmTraveled;
    }

    public void setKmTraveled(int kmTraveled) {
        this.kmTraveled = kmTraveled;
    }

    public boolean isFulled() {
        return fulled;
    }

    public void setFulled(boolean fulled) {
        this.fulled = fulled;
    }

    public String getCreationDate() {
        return creationDate.toString();
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public float getRefuelConsumption() {
        return refuelConsumption;
    }

    public void setRefuelConsumption(float refuelConsumption) {
        this.refuelConsumption = refuelConsumption;
    }

    public float getRefueledLiters() {
        return refueledLiters;
    }

    public void setRefueledLiters(float refueledLiters) {
        this.refueledLiters = refueledLiters;
    }

    public float getMedConsumption() {
        return medConsumption;
    }

    public void setMedConsumption(float medConsumption) {
        this.medConsumption = medConsumption;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }
}
