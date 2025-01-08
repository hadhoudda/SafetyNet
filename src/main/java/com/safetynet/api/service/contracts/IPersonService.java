package com.safetynet.api.service.contracts;

import com.safetynet.api.model.Person;

public interface IPersonService {

    boolean existPerson(Person person, String pathFile);

    boolean addPerson(Person person, String pathFile);

    boolean updatePerson(Person person, String pathFile);

    boolean deletePerson(Person person, String pathFile) ;

}