package com.safetynet.api.service;

import com.safetynet.api.constants.Path;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IDataJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalDate.parse;

@Service
public class PersonAgeService {
    @Autowired
    IDataJsonService dataJsonService ;
    @Autowired
    DataJsonContainer dataJsonContainer;


    public int calculAgePerson(String birthDayPerson){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Convertir la date de naissance en LocalDate
        LocalDate birthDate = parse(birthDayPerson, formatter);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears();

    }

    public int calculAdulPerson(List<Person> personList) throws IOException {
        int count = 0;
        dataJsonContainer = dataJsonService.readFileJson(Path.FILE_PATH);
        List<MedicalRecord> medicalRecordList = dataJsonContainer.getMedicalRecordList();
        for (Person person : personList){
            for (MedicalRecord medicalRecord : medicalRecordList){
                if(medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())){
                    calculAgePerson(medicalRecord.getBirthdate());
                    if (calculAgePerson(medicalRecord.getBirthdate()) > 18){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int calculChildPerson(List<Person> personList) throws IOException {
        int count = 0;
        dataJsonContainer = dataJsonService.readFileJson(Path.FILE_PATH);
        List<MedicalRecord> medicalRecordList = dataJsonContainer.getMedicalRecordList();
        for (Person person : personList){
            for (MedicalRecord medicalRecord : medicalRecordList){
                if(medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())){
                    calculAgePerson(medicalRecord.getBirthdate());
                    if (calculAgePerson(medicalRecord.getBirthdate()) <= 18){
                        count++;
                    }
                }
            }
        }
        return count;
    }
}