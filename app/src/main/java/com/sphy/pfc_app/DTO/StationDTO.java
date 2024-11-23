package com.sphy.pfc_app.DTO;

public class StationDTO {

    private long id;

    private String name;

    private String address;
    private String site;
    private String province;

    private boolean hide;
    private String registrationDate;

    private boolean favorite = false;

    private boolean glpFuel = false;
    private long userId;

    private int refuels = 0;

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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
