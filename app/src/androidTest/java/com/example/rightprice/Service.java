package com.example.rightprice;

public abstract class Service {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
    public abstract Vehicle[] getVehicles();



}
