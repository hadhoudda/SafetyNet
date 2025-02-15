package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

public class MedicalRecord {

    @JsonProperty("firstName")
    @NotBlank(message = "FirstName code is required")
    private String firstName;

    @JsonProperty("lastName")
    @NotBlank(message = "LastName code is required")
    private String lastName;

    @JsonProperty("birthdate")
    @NotBlank(message = "Birthdate code is required")
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }

    //Redefine equals for deleting an object if necessary
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MedicalRecord medicalRecord = (MedicalRecord) obj;
        return Objects.equals(firstName, medicalRecord.firstName) &&
                Objects.equals(lastName, medicalRecord.lastName) &&
                Objects.equals(birthdate, medicalRecord.birthdate) &&
                Objects.equals(medications, medicalRecord.medications) &&
                Objects.equals(allergies, medicalRecord.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthdate, medications, allergies);
    }
}