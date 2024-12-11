package com.safetynet.api.dao;

import com.safetynet.api.model.Person;

import java.util.List;

public interface PersonDao {
    Person findAll();
    Person findId(int id);
}
