package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
public class MedicalRecord {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("birthdate")
    private String birthdate;
    @JsonProperty("medications")
    private List<String> medications;
    @JsonProperty("allergies")
    private List<String> allergies;

    public MedicalRecord() {
    }

    public MedicalRecord(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

}
