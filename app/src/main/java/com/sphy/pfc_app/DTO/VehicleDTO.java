package com.sphy.pfc_app.DTO;

public class VehicleDTO {


    private long id;
    private String licensePlate;
    private String brand;
    private String model;
    private String fuel1;
    private String fuel2;
    private int kmActual;
    private int kmFuel1;
    private int kmFuel2;
    private float medConsumption;
    private String registrationDate;
    private int refuels;
    private boolean hide;
    private long userId;

    public VehicleDTO() {}

    public VehicleDTO(long id, String licensePlate, String brand, String model, String fuel1, String fuel2,
                      int kmActual, int kmFuel1, int kmFuel2, float medConsumption, String registrationDate,
                      int refuels, boolean hide, long userId) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.fuel1 = fuel1;
        this.fuel2 = fuel2;
        this.kmActual = kmActual;
        this.kmFuel1 = kmFuel1;
        this.kmFuel2 = kmFuel2;
        this.medConsumption = medConsumption;
        this.registrationDate = registrationDate;
        this.refuels = refuels;
        this.hide = hide;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getKmFuel1() {
        return kmFuel1;
    }

    public void setKmFuel1(int kmFuel1) {
        this.kmFuel1 = kmFuel1;
    }

    public int getKmFuel2() {
        return kmFuel2;
    }

    public void setKmFuel2(int kmFuel2) {
        this.kmFuel2 = kmFuel2;
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

    public int getRefuels() {
        return refuels;
    }

    public void setRefuels(int refuels) {
        this.refuels = refuels;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}