package com.safetynet.api.service.contracts;

import com.safetynet.api.model.Person;

import java.io.IOException;

public interface IPersonService {
    boolean addPerson(Person person);
    boolean updatePerson(Person person);
    boolean deletePerson(Person person) throws IOException;

}
