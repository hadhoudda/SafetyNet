package com.safetynet.api.dao;

import com.safetynet.api.constants.Path;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.dto.PersonListByFireStationWithCountDto;
import com.safetynet.api.dto.PersonSimplified;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.DataJsonService;
import com.safetynet.api.service.PersonService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiversServicePersonDaoImpl implements DiversServicePersonDao {

    DataJsonService dataJsonService = new DataJsonService();
    DataJsonContainer dataJsonContainer = new DataJsonContainer();
    PersonService personService = new PersonService();

    //methode filtre person selon numero de station
    public List<Person> filtrePersonByFirestation(String firestation){
        try {
            dataJsonContainer = dataJsonService.readFileJson(Path.FILE_PATH);
            //obtenir list les adresse de station indique
            List<String> addresses = dataJsonContainer.getFireStationList().stream()
                    .filter(station -> firestation.equals(station.getStation()))
                    .map(FireStation::getAddress).toList();
            System.out.println(addresses);
            //filtre list des person selon l'adresse de nombre de station indique
            List<Person> filteredPersons = dataJsonContainer.getPersonsList().stream()
                    .filter(person -> addresses.contains(person.getAddress()))  // Vérifier si l'adresse de la personne est dans la liste
                    .toList();

            return filteredPersons;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public PersonListByFireStationWithCountDto findAllPersonListByFireStationWithCount(String stationNumber) throws IOException{

            PersonListByFireStationWithCountDto personListByFireStationWithCountDto = new PersonListByFireStationWithCountDto();
            //filtre person selon numero de station indique
            List<Person> filteredPersons = filtrePersonByFirestation(stationNumber);

            // Créer une liste de PersonSimplified avec seulement les informations nécessaires
            List<PersonSimplified> simplifiedPersons = filteredPersons.stream()
                    .map(person -> new PersonSimplified(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
                    .toList();
            personListByFireStationWithCountDto.setPersons(simplifiedPersons);

            personListByFireStationWithCountDto.setAdultCount(personService.calculAdulPerson(filteredPersons));
            personListByFireStationWithCountDto.setChildCount(personService.calculChildPerson(filteredPersons));
            return personListByFireStationWithCountDto;


    }

    @Override
    public List<String> findAllListMailPersonByCity(String city) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(Path.FILE_PATH);
            List<String> listMail = dataJsonContainer.getPersonsList().stream()
                    .filter(person -> city.equals(person.getCity())).map(Person::getEmail).toList();


            return listMail;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<String> findAllListPhonePersonByFireStation(String firestation) {
        //filtre person selon numero de station indique
        List<Person> filteredPersons = filtrePersonByFirestation(firestation);
        List<String> phoneList = filteredPersons.stream().map(Person::getPhone).toList();
        return phoneList;
    }


}
