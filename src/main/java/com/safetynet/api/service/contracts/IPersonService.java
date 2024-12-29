package com.safetynet.api.service.contracts;

import com.safetynet.api.model.Person;

public interface IPersonService {
    boolean existPerson(Person person);
    boolean addPerson(Person person);
    boolean updatePerson(Person person);
    boolean deletePerson(Person person) ;
}