package com.sphy.pfc_app.domain;

import java.util.List;

public class Vehicle {


    private long id;
    private String licensePlate;
    private String brand;
    private String model;
    private String fuel1;
    private String fuel2;
    private int kmActual;
    private float medConsumption;
    private String registrationDate;
    private boolean hide;
    private List<Refuel> refuels;
    private long userId;

    public Vehicle() {}

    public Vehicle(int id, String licensePlate, String brand, String model, String fuel1, String fuel2, int kmActual, float medConsumption, String registrationDate, boolean hide, List<Refuel> refuels, long userId) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.fuel1 = fuel1;
        this.fuel2 = fuel2;
        this.kmActual = kmActual;
        this.medConsumption = medConsumption;
        this.registrationDate = registrationDate;
        this.hide = hide;
        this.refuels = refuels;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuel1() {
        return fuel1;
    }

    public void setFuel1(String fuel1) {
        this.fuel1 = fuel1;
    }

    public String getFuel2() {
        return fuel2;
    }

    public void setFuel2(String fuel2) {
        this.fuel2 = fuel2;
    }

    public int getKmActual() {
        return kmActual;
    }

    public void setKmActual(int kmActual) {
        this.kmActual = kmActual;
    }

    public float getMedConsumption() {
        return medConsumption;
    }

    public void setMedConsumption(float medConsumption) {
        this.medConsumption = medConsumption;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Refuel> getRefuels() {
        return refuels;
    }

    public void setRefuels(List<Refuel> refuels) {
        this.refuels = refuels;
    }


    public boolean getHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public long getUserId() {return userId;}

    public void setUserId(long userId) {this.userId = userId;}
}