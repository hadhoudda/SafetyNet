package com.safetynet.api.service;

import com.safetynet.api.constants.Path;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.safetynet.api.constants.Path.FILE_PATH;
import static java.time.LocalDate.parse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonAgeServiceTest {
    @InjectMocks
    PersonAgeService personAgeService;
    @Mock
    DataJsonContainer dataJsonContainer;
    @Mock
    DataJsonService dataJsonService;

    private List<Person> getPerson() {
        Person person1 = new Person("Alice", "Jean", "5 av lyon", "Paris", "123", "235648", "alice@mail.com");
        Person person2 = new Person("Sarah", "Krys", "12 place Monplier", "Nice", "457", "543896", "sarah@mail.com");
        return List.of(person1, person2);
    }

    private List<MedicalRecord> getMedicalRecord() {
        List<String> medications1 = List.of("neox 200mg");
        List<String> allergies1 = List.of("shellfish");
        MedicalRecord medicalRecord1 = new MedicalRecord("Alice", "Jean", "09/06/2000", medications1, allergies1);
        List<String> medications2 = List.of("desortadine");
        List<String> allergies2 = List.of("poulaine");
        MedicalRecord medicalRecord2 = new MedicalRecord("Sarah", "Krys", "02/10/2013", medications2, allergies2);
        return List.of(medicalRecord1, medicalRecord2);
    }

    @Test
    public void calculAgePersonTest() {
        // Arrange
        String birthDayPerson = "02/10/1983";
        LocalDate currentDate = LocalDate.now();
        // Date de naissance convertie
        LocalDate birthDate = LocalDate.parse(birthDayPerson, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        Period age = Period.between(birthDate, currentDate);
        // Act
        int result = personAgeService.calculAgePerson(birthDayPerson);
        // Assert
        assertEquals(age.getYears(), result);
        assertEquals(result, 41);
    }


    @Test
    public void calculAdulPersonTest() throws IOException {
        List<Person> personList = getPerson();
        List<MedicalRecord> medicalRecordList = getMedicalRecord();
        when(dataJsonService.readFileJson(FILE_PATH)).thenReturn(dataJsonContainer);
        lenient().when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        lenient().when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        int result = personAgeService.calculAdulPerson(personList);
        assertEquals(result, 1);
    }

    @Test
    public void calculChildPersonTest() throws IOException {
        List<Person> personList = getPerson();
        List<MedicalRecord> medicalRecordList = getMedicalRecord();
        when(dataJsonService.readFileJson(FILE_PATH)).thenReturn(dataJsonContainer);
        lenient().when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        lenient().when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        int result = personAgeService.calculChildPerson(personList);
        assertEquals(result, 1);
    }

}