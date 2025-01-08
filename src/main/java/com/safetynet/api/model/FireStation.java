package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class FireStation {

    @JsonProperty("address")
    @NotBlank(message = "Address code is required")
    private String address;

    @JsonProperty("station")
    @NotBlank(message = "Station code is required")
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

    @Override
    public String toString() {
        return "FireStation{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }

    //Redefine equals for deleting an object if necessary
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