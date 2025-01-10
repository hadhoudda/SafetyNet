package com.safetynet.api.unitaire.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.dto.PersonAndMedicalByAddressDto;
import com.safetynet.api.dto.PersonInfosDto;
import com.safetynet.api.dto.PersonListByFireStationWithCountDto;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonAgeService;
import com.safetynet.api.service.PersonInfoService;
import com.safetynet.api.service.contracts.IDataJsonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonInfoServiceTest {

    @Mock
    IDataJsonService dataJsonService ;
    @Mock
    DataJsonContainer dataJsonContainer ;
    @Mock
    PersonAgeService personAgeService ;
    @InjectMocks
    PersonInfoService personInfoService;

    private List<Person> getPerson() {
        Person person1 = new Person("Alice", "Jean", "5 av lyon", "Paris", "123", "235648", "alice@mail.com");
        Person person2 = new Person("Sarah", "Krys", "12 place Monplier", "Nice", "457", "543896", "sarah@mail.com");
        return List.of(person1, person2);
    }

    private List<FireStation> getFireStation() {
        FireStation fireStation1 = new FireStation("5 av lyon", "1");
        FireStation fireStation2 = new FireStation("12 place Monplier", "2");
        return List.of(fireStation1, fireStation2);
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
    public void filterPersonByFirestationTest() throws IOException {
        // Arrange
        List<FireStation> fireStations = getFireStation();
        List<Person> persons = getPerson();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStations);
        when(dataJsonContainer.getPersonsList()).thenReturn(persons);
        // Act
        List<Person> filteredPersons = personInfoService.filterPersonByFirestation("1", anyString()); //1: number of stations
        // Assert
        assertThat(filteredPersons).hasSize(1);
        assertThat(filteredPersons.getFirst().getFirstName()).isEqualTo("Alice");
        assertThat(filteredPersons.getFirst().getLastName()).isEqualTo("Jean");
    }

    @Test
    public void findAllPersonListByFireStationWithCountTest() throws IOException {
        // Arrange
        List<Person> personList = getPerson();
        List<MedicalRecord> medicalRecordList = getMedicalRecord();
        String pathFile = "src/test/resources/dataTest.json";
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        lenient().when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        when(personInfoService.filterPersonByFirestation("2", anyString())).thenReturn(personList); //2: number of stations
        lenient().when(personAgeService.calculateAdultPerson(personList, pathFile)).thenReturn(0);  // number of adults: 0
        lenient().when(personAgeService.calculateChildPerson(personList, pathFile)).thenReturn(1);  // number of children: 1
        // Act
        PersonListByFireStationWithCountDto result = personInfoService.findAllPersonListByFireStationWithCount("1", anyString());
        // Assert
        assertEquals(0, result.getAdultCount());
    }

    @Test
    public void findAllListPhonePersonByFireStationTest() throws IOException {
        // Arrange
        List<Person> personList = getPerson();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        when(personInfoService.filterPersonByFirestation("1", anyString())).thenReturn(personList);
        // Act
        List<String> phoneList = personInfoService.findAllListPhonePersonByFireStation("1", anyString()); //1: number of stations
        // Assert
        assertNotNull(phoneList);
        assertThat(personList.getFirst().getFirstName()).isEqualTo("Alice");
        assertThat(personList.getFirst().getLastName()).isEqualTo("Jean");
        assertThat(personList.getFirst().getPhone()).isEqualTo("235648");
    }

    @Test
    public void findAllChildByAddressAndPersonFromEvenHouseTest() throws IOException {
        // Arrange
        List<Person> personList = getPerson();
        List<MedicalRecord> medicalRecordList = getMedicalRecord();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        when(personAgeService.calculateAgePerson(anyString(), anyString())).thenReturn(11); // 11: age's child
        // Act
        Map<List<ChildAlertDto>, List<Person>> result = personInfoService.findAllChildByAddressAndPersonFromEvenHouse("12 place Monplier", anyString());
        // Assert
        assertNotNull(result);
        // Retrieve the list of ChildAlertDto which is the key of the map
        List<ChildAlertDto> childrenList = result.keySet().stream().findFirst().orElse(Collections.emptyList());
        assertEquals(1, childrenList.size());
        assertEquals("Sarah", childrenList.getFirst().getFirstName()); // Verify that the child is Sarah
    }

    @Test
    public void findAllPersonAndMedicalByAddressTest() throws IOException {
        // Arrange
        List<Person> persons = getPerson();
        List<MedicalRecord> medicalRecordList = getMedicalRecord();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(persons);
        when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        // Act
        Map<String, List<PersonAndMedicalByAddressDto>> result = personInfoService.findAllPersonAndMedicalByAddress("5 av lyon", anyString());
        // Assert
        assertThat(result).hasSize(1);
    }

    @Test
    public void findAllPersonGroupedByAddressTest() throws IOException {
        // Arrange
        List<Person> persons = getPerson();
        List<MedicalRecord> medicalRecordList = getMedicalRecord();
        List<FireStation> fireStationList = getFireStation();
        List<String> station = new ArrayList<>(List.of("1"));
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(persons);
        when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
        // Act
        Map<String, List<PersonAndMedicalByAddressDto>> result = personInfoService.findAllPersonGroupedByAddress(station, anyString()); //1: number of stations
        // Assert
        assertNotNull(result);
        Set<String> addressList = result.keySet();
        assertEquals(1, addressList.size());
    }

    @Test
    public void findAllPersonInfosTest() throws IOException {
        // Arrange
        List<Person> persons = getPerson();
        List<MedicalRecord> medicalRecordList = getMedicalRecord();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(persons);
        when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        // Act
        List<PersonInfosDto> result = personInfoService.findAllPersonInfos("Jean", anyString());
        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getFirstName()).isEqualTo("Alice");
        assertThat(result.getFirst().getLastName()).isEqualTo("Jean");
        List<String> listMedicamentTest = List.of("neox 200mg");
        assertThat(result.getFirst().getMedications()).isEqualTo(listMedicamentTest);
        List<String> listAllergiesTest = List.of("shellfish");
        assertThat(result.getFirst().getAllergies()).isEqualTo(listAllergiesTest);
    }

    @Test
    public void findAllListMailPersonByCityTest() throws IOException {
        // Arrange
        List<Person> persons = getPerson();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(persons);
        // Act
        List<String> listMail = personInfoService.findAllListMailPersonByCity("Nice"); //Nice: city
        // Assert
        assertThat(listMail).hasSize(1);
        assertThat(listMail.getFirst()).isEqualTo("sarah@mail.com");
    }
}