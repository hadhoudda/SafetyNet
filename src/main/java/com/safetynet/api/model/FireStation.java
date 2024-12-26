package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class FireStation {
    @JsonProperty("address")
    private String address;
    @JsonProperty("station")
    private String station;

    public FireStation() {
    }

    public FireStation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    //redefini equls pour l suppression d'un objet si nécessaire
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FireStation fireStation = (FireStation) obj;
        return Objects.equals(address, fireStation.address) &&
                Objects.equals(station, fireStation.station) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }
}
