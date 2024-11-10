package com.sphy.pfc_app.DTO;

public class RefuelDTO {

    private long id;
    private String fuel;
    private float amount;
    private float price;
    private String creationDate;
    private String licensePlate;
    private String stationName;
    private boolean fulled;


    public RefuelDTO() {}


    public RefuelDTO(long id, String fuel, float amount, float price, String creationDate, String licensePlate, String stationName, boolean fulled) {
        this.id = id;
        this.fuel = fuel;
        this.amount = amount;
        this.price = price;
        this.creationDate = creationDate;
        this.licensePlate = licensePlate;
        this.stationName = stationName;
        this.fulled = fulled;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public boolean isFulled() {
        return fulled;
    }

    public void setFulled(boolean fulled) {
        this.fulled = fulled;
    }
}
