package com.safetynet.api.dto;

import java.util.ArrayList;
import java.util.List;

public class PersonListByFireStationWithCountDto {

    private List<PersonSimplifiedDto> persons = new ArrayList<>();
    private int adultCount;
    private int childCount;

    public PersonListByFireStationWithCountDto() {
    }

    public PersonListByFireStationWithCountDto(List<PersonSimplifiedDto> persons, int adultCount, int childCount) {
        this.persons = persons;
        this.adultCount = adultCount;
        this.childCount = childCount;
    }

    public List<PersonSimplifiedDto> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonSimplifiedDto> persons) {
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