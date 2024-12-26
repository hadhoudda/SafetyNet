package com.safetynet.api.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IDataJsonService;
import com.safetynet.api.service.contracts.IPersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.safetynet.api.constants.Path.FILE_PATH;

@Service
public class PersonService implements IPersonService {

    private static final Logger logger = LogManager.getLogger(PersonService.class);

    @Autowired
    IDataJsonService dataJsonService;
    DataJsonContainer dataJsonContainer;

    private boolean existPerson(Person person) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(FILE_PATH);
            List<String> listLastName = dataJsonContainer.getPersonsList().stream()
                    .map(Person::getLastName).toList();

            List<String> listFirstName = dataJsonContainer.getPersonsList().stream()
                    .map(Person::getFirstName).toList();

            return listFirstName.contains(person.getFirstName()) && listLastName.contains(person.getLastName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addPerson(Person person) {
        try {
            boolean personExist = existPerson(person);
            if (personExist) {
                logger.error("person exists");
                return false;
            } else {
                dataJsonContainer.getPersonsList().add(person);
                dataJsonService.writeFileJson(dataJsonContainer);
                logger.info("Person added successfully");
                return true;
            }
        } catch (Exception e) {
            logger.error("Error writing to JSON file", e);
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }

    @Override
    public boolean updatePerson(Person person) {
        try {
            boolean personExist = false;
            for (int i = 0; i < dataJsonContainer.getPersonsList().size(); i++) {
                Person existingPerson = dataJsonContainer.getPersonsList().get(i);
                if (existingPerson.getLastName().equals(person.getLastName()) && existingPerson.getFirstName().equals(person.getFirstName())) {
                    dataJsonContainer.getPersonsList().set(i, person); //mettre à jour person
                    personExist = true;
                    break;
                }
            }
            if (personExist) {
                dataJsonService.writeFileJson(dataJsonContainer);
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

    @Override
    public boolean deletePerson(Person person) {
        try {
            if (existPerson(person)){
                dataJsonContainer.getPersonsList().remove(person);
                dataJsonService.writeFileJson(dataJsonContainer);
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