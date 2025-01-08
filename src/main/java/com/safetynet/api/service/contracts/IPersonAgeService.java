package com.safetynet.api.service.contracts;

import com.safetynet.api.model.Person;

import java.io.IOException;
import java.util.List;

public interface IPersonAgeService {
    int calculateAgePerson(String birthDayPerson, String filePath);

    int calculateAdultPerson(List<Person> personList, String filePath)throws IOException;

    int calculateChildPerson(List<Person> personList, String filePath) throws IOException;

}
