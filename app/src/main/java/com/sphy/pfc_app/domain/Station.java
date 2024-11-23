package com.sphy.pfc_app.domain;

import java.time.LocalDate;
import java.util.List;

public class Station {


    private long id;

    private String name;
    private String address;
    private String site;
    private String province;
    private String registrationDate;
    private boolean favorite = false;
    private boolean glpFuel = false;
    private boolean hide;
    private long userId;
    private List<Refuel> refuels;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isGlpFuel() {
        return glpFuel;
    }

    public void setGlpFuel(boolean glpFuel) {
        this.glpFuel = glpFuel;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public List<Refuel> getRefuels() {
        return refuels;
    }

    public void setRefuels(List<Refuel> refuels) {
        this.refuels = refuels;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

