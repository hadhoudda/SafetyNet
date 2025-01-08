package com.safetynet.api.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IDataJsonService;
import com.safetynet.api.service.contracts.IPersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements IPersonService {

    private static final Logger logger = LogManager.getLogger(PersonService.class);
    @Autowired
    IDataJsonService dataJsonService;
    DataJsonContainer dataJsonContainer;

    /**
     * Method to check the existence of person
     *
     * @param person:   object
     * @param pathFile: file link
     * @return true: person exists or false: person is not exists
     */
    @Override
    public boolean existPerson(Person person, String pathFile) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            return dataJsonContainer.getPersonsList().stream()
                    .anyMatch(p -> p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName()));
        } catch (Exception e) {
            logger.error("Error reading file or verify person: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Methode to add person
     *
     * @param person:   object
     * @param pathFile: file link
     * @return true: person adding or false: didn't add
     */
    @Override
    public boolean addPerson(Person person, String pathFile) {
        try {
            if (existPerson(person, pathFile)) { //verify person exists
                logger.error("person exists");
                return false;
            } else {
                dataJsonContainer.getPersonsList().add(person);
                dataJsonService.writeFileJson(dataJsonContainer, pathFile);
                logger.info("Person added successfully");
                return true;
            }
        } catch (Exception e) {
            logger.error("Error writing to JSON file", e);
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }

    /**
     * Methode to update person
     * @param person: object
     * @param pathFile: file link
     * @return true: person updating or false: didn't update
     */
    @Override
    public boolean updatePerson(Person person, String pathFile) {
        try {
            boolean personExist = false;
            for (int i = 0; i < dataJsonContainer.getPersonsList().size(); i++) {
                Person existingPerson = dataJsonContainer.getPersonsList().get(i);
                if (existingPerson.getLastName().equals(person.getLastName()) && existingPerson.getFirstName().equals(person.getFirstName())) {
                    dataJsonContainer.getPersonsList().set(i, person); //update person
                    personExist = true;
                    break;
                }
            }
            if (personExist) {
                dataJsonService.writeFileJson(dataJsonContainer, pathFile);
                logger.info("Successful updated Person ");
                return true;
            } else {
                logger.info("person is not exists");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error writing to JSON file", e);
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }

    /**
     * Methode to delete person
     * @param person: object
     * @param pathFile: file link
     * @return true: person deleting or false: didn't delete
     */
    @Override
    public boolean deletePerson(Person person, String pathFile) {
        try {
            if (existPerson(person, pathFile)) { //verify person exists
                dataJsonContainer.getPersonsList().remove(person);
                dataJsonService.writeFileJson(dataJsonContainer, pathFile);
                logger.info("Successful deleted person ");
                return true;
            } else {
                logger.error("Error : person is not exists");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error writing to JSON file", e);
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }
}