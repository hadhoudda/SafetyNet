package com.safetynet.api.container;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataJsonContainer {

    @JsonProperty("persons")
    private List<Person> personsList;
    @JsonProperty("firestations")
    private List<FireStation> fireStationList;
    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecordList;

}
