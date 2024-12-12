package com.safetynet.api.container;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;


import java.util.List;

public class DataJsonContainer {
    @JsonProperty("persons")
    private List<Person> personsList;
    @JsonProperty("firestations")
    private List<FireStation> fireStationList;
    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecordList;

    public DataJsonContainer(List<Person> personsList, List<FireStation> fireStationList, List<MedicalRecord> medicalRecordList) {
        this.personsList = personsList;
        this.fireStationList = fireStationList;
        this.medicalRecordList = medicalRecordList;
    }

    public DataJsonContainer(){    }

    public List<Person> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<Person> personsList) {
        this.personsList = personsList;
    }

    public List<FireStation> getFireStationList() {
        return fireStationList;
    }

    public void setFireStationList(List<FireStation> fireStationList) {
        this.fireStationList = fireStationList;
    }

    public List<MedicalRecord> getMedicalRecordList() {
        return medicalRecordList;
    }

    public void setMedicalRecordList(List<MedicalRecord> medicalRecordList) {
        this.medicalRecordList = medicalRecordList;
    }
}