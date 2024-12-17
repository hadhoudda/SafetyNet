package com.safetynet.api.dto;

import com.safetynet.api.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonListByFireStationWithCountDto {

    private List<PersonSimplified> persons = new ArrayList<>();
    private int adultCount;
    private int childCount;


    public List<PersonSimplified> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonSimplified> persons) {
        this.persons = persons;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }
}