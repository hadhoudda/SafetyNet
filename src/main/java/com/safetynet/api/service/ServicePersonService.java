package com.safetynet.api.service;

import com.safetynet.api.constants.Path;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.dto.PersonAndMedicalRecordDto;
import com.safetynet.api.dto.PersonListByFireStationWithCountDto;
import com.safetynet.api.dto.PersonSimplifiedDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IServicePersonService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicePersonService implements IServicePersonService {

    DataJsonService dataJsonService = new DataJsonService();
    DataJsonContainer dataJsonContainer = new DataJsonContainer();
    PersonService personService = new PersonService();

    //methode filtre person selon numero de station
    @Override
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
        List<PersonSimplifiedDto> simplifiedPersons = filteredPersons.stream()
                .map(person -> new PersonSimplifiedDto(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
                    .toList();
            personListByFireStationWithCountDto.setPersons(simplifiedPersons);

            personListByFireStationWithCountDto.setAdultCount(personService.calculAdulPerson(filteredPersons));
            personListByFireStationWithCountDto.setChildCount(personService.calculChildPerson(filteredPersons));
            return personListByFireStationWithCountDto;

    }


    @Override
    public List<String> findAllListPhonePersonByFireStation(String firestation) {
        //filtre person selon numero de station indique
        List<Person> filteredPersons = filtrePersonByFirestation(firestation);
        List<String> phoneList = filteredPersons.stream().map(Person::getPhone).toList();
        return phoneList;
    }


    @Override
    public Map<List<ChildAlertDto>, List<Person>> findAllChildByAdress(String address) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(Path.FILE_PATH);
            List<MedicalRecord> medicalRecordList = dataJsonContainer.getMedicalRecordList();
            List<Person> personList = dataJsonContainer.getPersonsList();
            List<ChildAlertDto> childrenList = new ArrayList<>();
            Map<List<ChildAlertDto>, List<Person>> list = new HashMap<>();

            for (Person person : personList) {
                for (MedicalRecord medicalRecord : medicalRecordList) {
                    if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
                        int age = personService.calculAgePerson(medicalRecord.getBirthdate());
                        if (age <= 18 && person.getAddress().equals(address)) {
                            childrenList.add(new ChildAlertDto(person.getFirstName(), person.getLastName(), age));

                        }
                    }
                }
            }

            if (childrenList.isEmpty()) {
                personList.removeAll(personList);
            }
            for (ChildAlertDto childAlertDTO : childrenList) {
                // Supprimer les éléments dont le lastName ne correspond pas au lastName de l'enfant
                personList.removeIf(person -> !childAlertDTO.getLastName().equals(person.getLastName()));
                //supprime l'enfant de list
                personList.removeIf(person -> childAlertDTO.getFirstName().equals(person.getFirstName()));
            }

            list.put(childrenList, personList);

            System.out.println(childrenList);
            return list;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<PersonAndMedicalRecordDto> findAllPersonAndMedicalByAdress(String adress) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(Path.FILE_PATH);
            List<Person> personList = dataJsonContainer.getPersonsList();
            List<MedicalRecord> medicalRecordList = dataJsonContainer.getMedicalRecordList();
            List<PersonAndMedicalRecordDto> personMedicalRecordMap = new ArrayList<>();

            personList = personList.stream().filter(person -> adress.equals(person.getAddress())).toList();
            System.out.println(personList);

            for (Person person : personList) {
                for (MedicalRecord medicalRecord : medicalRecordList) {
                    if (person.getLastName().equals(medicalRecord.getLastName()) && person.getFirstName().equals(medicalRecord.getFirstName())) {
                        int age = personService.calculAgePerson(medicalRecord.getBirthdate());
                        personMedicalRecordMap.add(new PersonAndMedicalRecordDto(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getPhone(),
                                age,
                                medicalRecord.getMedications(),
                                medicalRecord.getAllergies()));
                    }
                }
            }
            return personMedicalRecordMap;
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }

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
}
