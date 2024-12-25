package com.safetynet.api.service.contracts;

import com.safetynet.api.model.Person;

import java.io.IOException;

public interface IPersonService {
    Person addPerson(Person person);
    Person updatePerson(Person person);
    boolean deletePerson(Person person) throws IOException;

}
