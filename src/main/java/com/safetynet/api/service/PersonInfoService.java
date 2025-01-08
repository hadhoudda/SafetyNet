package com.safetynet.api.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.dto.*;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IDataJsonService;
import com.safetynet.api.service.contracts.IPersonAgeService;
import com.safetynet.api.service.contracts.IPersonInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.safetynet.api.constants.Path.FILE_PATH;

@Service
public class PersonInfoService implements IPersonInfoService {

    private static final Logger logger = LogManager.getLogger(PersonInfoService.class);
    @Autowired
    IDataJsonService dataJsonService;
    @Autowired
    DataJsonContainer dataJsonContainer;
    @Autowired
    IPersonAgeService personAgeService;
    private final String pathFile = FILE_PATH;

    /**
     * Method to filter persons according to station number
     *
     * @param firestation:number of stations
     * @return: list persons
     */
    @Override
    public List<Person> filterPersonByFirestation(String firestation, String pathFile) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            //get list of station addresses indicated
            List<String> addresses = dataJsonContainer.getFireStationList().stream()
                    .filter(station -> firestation.equals(station.getStation()))
                    .map(FireStation::getAddress).toList();
            System.out.println(addresses);
            //filter list of persons according to the address of the station number indicated
            List<Person> filteredPersons = dataJsonContainer.getPersonsList().stream().filter(person -> addresses.contains(person.getAddress()))  // Check if the person's address is in the list
                    .toList();
            logger.info("List persons according to station number: successfully retrieved");
            return filteredPersons;
        } catch (Exception e) {
            throw new RuntimeException("Error while reading the JSON file", e);
        }
    }

    /**
     * Method to filter persons according to station number and send the necessary person information
     *
     * @param stationNumber: number of stations
     * @return: personListByFireStationWithCountDto
     */
    @Override
    public PersonListByFireStationWithCountDto findAllPersonListByFireStationWithCount(String stationNumber, String pathFile) throws IOException {
        PersonListByFireStationWithCountDto personListByFireStationWithCountDto = new PersonListByFireStationWithCountDto();
        //personal filter according to station number indicated
        List<Person> filteredPersons = filterPersonByFirestation(stationNumber, pathFile);
        // Create a PersonSimplified listing with only the necessary information
        List<PersonSimplifiedDto> simplifiedPersons = filteredPersons.stream()
                .map(person -> new PersonSimplifiedDto(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()))
                .toList();
        personListByFireStationWithCountDto.setPersons(simplifiedPersons);
        personListByFireStationWithCountDto.setAdultCount(personAgeService.calculateAdultPerson(filteredPersons, pathFile));//count persons adult
        personListByFireStationWithCountDto.setChildCount(personAgeService.calculateChildPerson(filteredPersons, pathFile));//count persons child
        return personListByFireStationWithCountDto;
    }

    /**
     * Method to obtain the list of phone numbers of persons corresponding to the indicated firestation
     *
     * @param firestation: number of stations
     * @return: list of phone numbers
     */
    @Override
    public List<String> findAllListPhonePersonByFireStation(String firestation, String pathFile) {
        //personal filter according to station number indicated
        List<Person> filteredPersons = filterPersonByFirestation(firestation, pathFile);
        List<String> phoneList = filteredPersons.stream().map(Person::getPhone).toList();
        return phoneList;
    }

    /**
     * Method to obtain list of children with indicated address and persons from the same household
     *
     * @param address: address name
     * @return: map child and person from the same household
     */
    @Override
    public Map<List<ChildAlertDto>, List<Person>> findAllChildByAddressAndPersonFromEvenHouse(String address, String pathFile) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            List<MedicalRecord> medicalRecordList = dataJsonContainer.getMedicalRecordList();
            List<Person> personList = dataJsonContainer.getPersonsList();
            List<Person> modifiablePersonList = new ArrayList<>(personList);
            List<ChildAlertDto> childrenList = new ArrayList<>();
            Map<List<ChildAlertDto>, List<Person>> list = new HashMap<>();
            //to obtain list of child
            for (Person person : personList) {
                for (MedicalRecord medicalRecord : medicalRecordList) {
                    if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
                        int age = personAgeService.calculateAgePerson(medicalRecord.getBirthdate(), pathFile);
                        if (age <= 18 && person.getAddress().equals(address)) {
                            childrenList.add(new ChildAlertDto(person.getFirstName(), person.getLastName(), age));
                        }
                    }
                }
            }
            if (childrenList.isEmpty()) {
                modifiablePersonList.removeAll(personList);
            }
            for (ChildAlertDto childAlertDTO : childrenList) {
                // Remove elements whose lastName does not match the child's lastName
                modifiablePersonList.removeIf(person -> !childAlertDTO.getLastName().equals(person.getLastName()));
                //remove child from person list so as not to display twice
                modifiablePersonList.removeIf(person -> childAlertDTO.getFirstName().equals(person.getFirstName()));
            }
            list.put(childrenList, modifiablePersonList);
            System.out.println(childrenList);
            logger.info("List of children with indicated address and persons from the same household: successfully retrieved");
            return list;
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the JSON file", e);
        }
    }

    /**
     * Method to obtain list of persons and their medicalRecord according to the address indicated
     *
     * @param address: the address indicated
     * @return: list of persons and their medicalRecords
     */
    @Override
    public Map<String, List<PersonAndMedicalByAddressDto>> findAllPersonAndMedicalByAddress(String address, String pathFile) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            List<Person> personList = dataJsonContainer.getPersonsList();
            List<MedicalRecord> medicalRecordList = dataJsonContainer.getMedicalRecordList();
            List<FireStation> fireStationList = dataJsonContainer.getFireStationList();
            List<PersonAndMedicalByAddressDto> personMedicalRecord = new ArrayList<>();
            //filter persons according to the address indicated
            personList = personList.stream().filter(person -> address.equals(person.getAddress())).toList();
            System.out.println(personList);
            for (Person person : personList) {
                for (MedicalRecord medicalRecord : medicalRecordList) {
                    if (person.getLastName().equals(medicalRecord.getLastName()) && person.getFirstName().equals(medicalRecord.getFirstName())) {
                        int age = personAgeService.calculateAgePerson(medicalRecord.getBirthdate(), pathFile);
                        personMedicalRecord.add(new PersonAndMedicalByAddressDto(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getPhone(),
                                age,
                                medicalRecord.getMedications(),
                                medicalRecord.getAllergies()));
                    }
                }
            }
            //obtain number firestation
            String numberFireStation = fireStationList.stream().filter(fireStation -> address.equals(fireStation.getAddress())).map(FireStation::getStation)  // Supposons que getName() retourne le nom de la caserne
                    .findFirst().orElse("");
            logger.info("List of persons and their medicalRecord according to the address indicated: successfully retrieved");
            Map<String, List<PersonAndMedicalByAddressDto>> medicalRecordsMap = new HashMap<>();
            medicalRecordsMap.put(numberFireStation, personMedicalRecord);
            return medicalRecordsMap;
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("Error while reading the JSON file", e);
        }
    }

    /**
     * Method to obtain list of persons group according to address
     *
     * @param station: number of stations
     * @return: map of persons
     */
    @Override
    public Map<String, List<PersonAndMedicalByAddressDto>> findAllPersonGroupedByAddress(String station, String pathFile) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            List<Person> personList = dataJsonContainer.getPersonsList();
            List<MedicalRecord> medicalRecordList = dataJsonContainer.getMedicalRecordList();
            List<FireStation> fireStationList = dataJsonContainer.getFireStationList();
            Map<String, List<PersonAndMedicalByAddressDto>> medicalRecordsMap = new HashMap<>();
            // Filter addresses according to the indicated station
            List<String> addressesList = fireStationList.stream().filter(firestation -> station.equals(firestation.getStation())).map(FireStation::getAddress).toList();
            // Filter person based on addresses
            List<Person> filteredPersons = personList.stream().filter(person -> addressesList.contains(person.getAddress())).toList();
            // Browse filtered person
            for (Person person : filteredPersons) {
                List<PersonAndMedicalByAddressDto> personMedicalRecord = new ArrayList<>();
                for (MedicalRecord medicalRecord : medicalRecordList) {
                    if (person.getLastName().equals(medicalRecord.getLastName()) && person.getFirstName().equals(medicalRecord.getFirstName())) {
                        int age = personAgeService.calculateAgePerson(medicalRecord.getBirthdate(), pathFile);
                        personMedicalRecord.add(new PersonAndMedicalByAddressDto(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getPhone(),
                                age,
                                medicalRecord.getMedications(), medicalRecord.getAllergies()));
                    }
                }
                //if the list of person for an address exists, we add it to the list, otherwise we create it
                if (!personMedicalRecord.isEmpty()) {
                    String address = person.getAddress();
                    medicalRecordsMap.computeIfAbsent(address, k -> new ArrayList<>()).addAll(personMedicalRecord);
                }
            }
            logger.info("List of persons grouped by address : successfully retrieved");
            return medicalRecordsMap;

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("Error while reading the JSON file", e);
        }
    }

    /**
     * Method to obtain all the information of a person according to lastName
     * @param lastName: lastName of person
     * @return list of infos
     */
    @Override
    public List<PersonInfosDto> findAllPersonInfos(String lastName, String pathFile) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            List<Person> personList = dataJsonContainer.getPersonsList();
            List<MedicalRecord> medicalRecordList = dataJsonContainer.getMedicalRecordList();
            List<PersonInfosDto> personInfosDtoList = new ArrayList<>();
            //filter list persons according to lastName
            personList = personList.stream().filter(person -> lastName.equals(person.getLastName())).toList();
            for(Person person: personList){
                for (MedicalRecord medicalRecord: medicalRecordList) {
                    if (person.getLastName().equals(medicalRecord.getLastName()) && person.getFirstName().equals(medicalRecord.getFirstName())) {
                        int age = personAgeService.calculateAgePerson(medicalRecord.getBirthdate(), pathFile);
                        personInfosDtoList.add(new PersonInfosDto(
                                    person.getFirstName(),
                                    lastName,
                                    person.getAddress(),
                                    age,
                                    person.getEmail(),
                                    medicalRecord.getMedications(),
                                    medicalRecord.getAllergies()
                            ));
                    }
                }
            }
            logger.info("List all the information of a person according to lastName: successfully retrieved");
            return personInfosDtoList;
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException("Error while reading the JSON file", e);
        }
    }

    /**
     * Method to obtain list of emails by persons according to city
     * @param city: city indicated
     * @return: list email of persons
     */
    @Override
    public List<String> findAllListMailPersonByCity(String city) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            List<String> listMail = dataJsonContainer.getPersonsList().stream()
                    .filter(person -> city.equals(person.getCity())).map(Person::getEmail).toList();
            logger.info("List of emails by persons according to city: successfully retrieved");
            return listMail;
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the JSON file", e);
        }
    }
}