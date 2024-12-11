package com.safetynet.api.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class PersonDaoImpl implements PersonDao{

    //private List<Person> persons = new ArrayList<>();
    @Autowired
    Person person;

    @Override
    public Person findAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Lire le fichier JSON et le convertir en objet Java
            return objectMapper.readValue(new File("src/main/resources/data.json"), Person.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person findId(int id) {
        return null;
    }
}
