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
    private int kmTraveled1;
    private int kmTraveled2;
    private boolean fulled;
    private String creationDate;
    private float refuelConsumption;
    private float refueledLiters;
    private float medConsumption;



    private boolean doubleRefuel;
    private String secondFuel;
    private float secondAmount;
    private float secondPrice;
    private boolean secondFulled;
    private int kmsTraveledSecondrefuel;
    private float secondRefuelConsumption;
    private float secondRefueledLiters;
    private float secondMedConsumption;


    private long vehicleId;
    private long stationId;

    private long userId;


    public Refuel() {
    }

    public Refuel(long id, String nameStation, String nameVehicle, String fuel, float amount, float price, int kmTotal,
                  int kmTraveled1, int kmTraveled2, boolean fulled, String creationDate, float refuelConsumption,
                  float refueledLiters, float medConsumption, boolean doubleRefuel, String secondFuel,
                  float secondAmount, float secondPrice, boolean secondFulled, int kmsTraveledSecondrefuel,
                  float secondRefuelConsumption, float secondRefueledLiters, float secondMedConsumption,
                  long vehicleId, long stationId, long userId) {
        this.id = id;
        this.nameStation = nameStation;
        this.nameVehicle = nameVehicle;
        this.fuel = fuel;
        this.amount = amount;
        this.price = price;
        this.kmTotal = kmTotal;
        this.kmTraveled1 = kmTraveled1;
        this.kmTraveled2 = kmTraveled2;
        this.fulled = fulled;
        this.creationDate = creationDate;
        this.refuelConsumption = refuelConsumption;
        this.refueledLiters = refueledLiters;
        this.medConsumption = medConsumption;
        this.doubleRefuel = doubleRefuel;
        this.secondFuel = secondFuel;
        this.secondAmount = secondAmount;
        this.secondPrice = secondPrice;
        this.secondFulled = secondFulled;
        this.kmsTraveledSecondrefuel = kmsTraveledSecondrefuel;
        this.secondRefuelConsumption = secondRefuelConsumption;
        this.secondRefueledLiters = secondRefueledLiters;
        this.secondMedConsumption = secondMedConsumption;
        this.vehicleId = vehicleId;
        this.stationId = stationId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Refuel{" +
                "id=" + id +
                ", nameStation='" + nameStation + '\'' +
                ", nameVehicle='" + nameVehicle + '\'' +
                ", fuel='" + fuel + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", kmTotal=" + kmTotal +
                ", kmTraveled1=" + kmTraveled1 +
                ", kmTraveled2=" + kmTraveled2 +
                ", fulled=" + fulled +
                ", creationDate='" + creationDate + '\'' +
                ", refuelConsumption=" + refuelConsumption +
                ", refueledLiters=" + refueledLiters +
                ", medConsumption=" + medConsumption +
                ", doubleRefuel=" + doubleRefuel +
                ", secondFuel='" + secondFuel + '\'' +
                ", secondAmount=" + secondAmount +
                ", secondPrice=" + secondPrice +
                ", secondFulled=" + secondFulled +
                ", kmsTraveledSecondrefuel=" + kmsTraveledSecondrefuel +
                ", secondRefuelConsumption=" + secondRefuelConsumption +
                ", secondRefueledLiters=" + secondRefueledLiters +
                ", secondMedConsumption=" + secondMedConsumption +
                ", vehicleId=" + vehicleId +
                ", stationId=" + stationId +
                ", userId=" + userId +
                '}';
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

    public int getKmTraveled1() {
        return kmTraveled1;
    }

    public void setKmTraveled1(int kmTraveled1) {
        this.kmTraveled1 = kmTraveled1;
    }

    public int getKmTraveled2() {
        return kmTraveled2;
    }

    public void setKmTraveled2(int kmTraveled2) {
        this.kmTraveled2 = kmTraveled2;
    }

    public boolean isFulled() {
        return fulled;
    }

    public void setFulled(boolean fulled) {
        this.fulled = fulled;
    }

    public String getCreationDate() {
        return creationDate;
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

    public boolean isDoubleRefuel() {
        return doubleRefuel;
    }

    public void setDoubleRefuel(boolean doubleRefuel) {
        this.doubleRefuel = doubleRefuel;
    }

    public String getSecondFuel() {
        return secondFuel;
    }

    public void setSecondFuel(String secondFuel) {
        this.secondFuel = secondFuel;
    }

    public float getSecondAmount() {
        return secondAmount;
    }

    public void setSecondAmount(float secondAmount) {
        this.secondAmount = secondAmount;
    }

    public float getSecondPrice() {
        return secondPrice;
    }

    public void setSecondPrice(float secondPrice) {
        this.secondPrice = secondPrice;
    }

    public boolean isSecondFulled() {
        return secondFulled;
    }

    public void setSecondFulled(boolean secondFulled) {
        this.secondFulled = secondFulled;
    }

    public int getKmsTraveledSecondrefuel() {
        return kmsTraveledSecondrefuel;
    }

    public void setKmsTraveledSecondrefuel(int kmsTraveledSecondrefuel) {
        this.kmsTraveledSecondrefuel = kmsTraveledSecondrefuel;
    }

    public float getSecondRefuelConsumption() {
        return secondRefuelConsumption;
    }

    public void setSecondRefuelConsumption(float secondRefuelConsumption) {
        this.secondRefuelConsumption = secondRefuelConsumption;
    }

    public float getSecondRefueledLiters() {
        return secondRefueledLiters;
    }

    public void setSecondRefueledLiters(float secondRefueledLiters) {
        this.secondRefueledLiters = secondRefueledLiters;
    }

    public float getSecondMedConsumption() {
        return secondMedConsumption;
    }

    public void setSecondMedConsumption(float secondMedConsumption) {
        this.secondMedConsumption = secondMedConsumption;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
