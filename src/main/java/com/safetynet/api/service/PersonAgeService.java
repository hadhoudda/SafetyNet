package com.safetynet.api.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IDataJsonService;
import com.safetynet.api.service.contracts.IPersonAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.safetynet.api.constants.Path.FILE_PATH;
import static java.time.LocalDate.parse;

@Service
public class PersonAgeService implements IPersonAgeService {

    @Autowired
    IDataJsonService dataJsonService ;
    @Autowired
    DataJsonContainer dataJsonContainer;
    String pathFile = FILE_PATH;

    /**
     * Method to calculate person's age
     *
     * @param birthDayPerson: date of birth
     * @return: integer
     */
    @Override
    public int calculateAgePerson(String birthDayPerson, String pathFile) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Convert date of birth to LocalDate
        LocalDate birthDate = parse(birthDayPerson, formatter);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears();
    }

    /**
     * Method to count adult
     *
     * @param personList: list person
     * @return: integer
     */
    @Override
    public int calculateAdultPerson(List<Person> personList, String pathFile) throws IOException {
        int count = 0;
        dataJsonContainer = dataJsonService.readFileJson(FILE_PATH);
        List<MedicalRecord> medicalRecordList = dataJsonContainer.getMedicalRecordList();
        for (Person person : personList){
            for (MedicalRecord medicalRecord : medicalRecordList) {
                if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
                    calculateAgePerson(medicalRecord.getBirthdate(), pathFile);
                    if (calculateAgePerson(medicalRecord.getBirthdate(), pathFile) > 18) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Method to count child
     *
     * @param personList: list person
     * @return: integer
     */
    @Override
    public int calculateChildPerson(List<Person> personList, String pathFile) throws IOException {
        int count = 0;
        dataJsonContainer = dataJsonService.readFileJson(pathFile);
        List<MedicalRecord> medicalRecordList = dataJsonContainer.getMedicalRecordList();
        for (Person person : personList){
            for (MedicalRecord medicalRecord : medicalRecordList) {
                if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
                    calculateAgePerson(medicalRecord.getBirthdate(), pathFile);
                    if (calculateAgePerson(medicalRecord.getBirthdate(), pathFile) <= 18) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}