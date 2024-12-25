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

    public boolean existPerson(Person person) {
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
    public Person addPerson(Person person) {
        try {
            boolean personExist = existPerson(person);
            if (personExist) {
                logger.info("person existe");
            } else {
                dataJsonContainer.getPersonsList().add(person);
                dataJsonService.writeFileJson(dataJsonContainer);
                logger.info("Person ajoutée avec succès");
            }
            return person;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person updatePerson(Person person) {
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
                logger.info("Mise à jour de la personne réussie");
            } else {
                logger.info("person n'existe pas");
            }
            return person;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la personne", e);
        }
    }

    @Override
    public boolean deletePerson(Person person) {
        try {
            dataJsonService.readFileJson(FILE_PATH);
            if (existPerson(person)){
                dataJsonContainer.getPersonsList().remove(person);
                dataJsonService.writeFileJson(dataJsonContainer);
                return true;
            } else {
                logger.info("Erreur : person n'existe pas ");
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la personne", e);
        }
    }

}