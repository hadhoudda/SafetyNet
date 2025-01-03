package com.safetynet.api.unitaire.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.dto.PersonAndMedicalRecordDto;
import com.safetynet.api.dto.PersonInfos;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    //private Person person3 = new Person("Maxime", "Rock", "5 av lyon", "Paris", "123", "489575", "maxime@mail.com");
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
    public void filtrePersonByFirestationTest() throws IOException {
        // Arrange
        List<FireStation> fireStations = getFireStation();
        List<Person> persons = getPerson();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStations);
        when(dataJsonContainer.getPersonsList()).thenReturn(persons);
        // Act
        List<Person> filteredPersons = personInfoService.filtrePersonByFirestation("1");
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
        PersonListByFireStationWithCountDto personListByFireStationWithCountDto = new PersonListByFireStationWithCountDto();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        lenient().when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        when(personInfoService.filtrePersonByFirestation("2")).thenReturn(personList);
        lenient().when(personAgeService.calculAdulPerson(personList)).thenReturn(0);  // nombre d'adultes: 0
        lenient().when(personAgeService.calculChildPerson(personList)).thenReturn(1);  // nombre d'enfants: 1
        // Act
        PersonListByFireStationWithCountDto result = personInfoService.findAllPersonListByFireStationWithCount("1");
        // Assert
        assertEquals(0, result.getAdultCount());
    }


    @Test
    public void findAllListPhonePersonByFireStationTest() throws IOException {
        // Arrange
        List<Person> personList = getPerson();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        when(personInfoService.filtrePersonByFirestation("1")).thenReturn(personList);
        // Act
        List<String> phoneList = personInfoService.findAllListPhonePersonByFireStation("1");
        // Assert
        assertNotNull(phoneList);
        assertThat(personList.getFirst().getFirstName()).isEqualTo("Alice");
        assertThat(personList.getFirst().getLastName()).isEqualTo("Jean");
        assertThat(personList.getFirst().getPhone()).isEqualTo("235648");

    }

    @Test
    public void findAllChildByAdressTest() throws IOException {
        // Arrange
        List<Person> personList = getPerson();
        List<MedicalRecord> medicalRecordList = getMedicalRecord();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        when(personAgeService.calculAgePerson(anyString())).thenReturn(11);
        // Act
        Map<List<ChildAlertDto>, List<Person>> result = personInfoService.findAllChildByAdress("12 place Monplier");
        // Assert
        assertNotNull(result);
        // Récupérer la liste de ChildAlertDto qui est la clé de la map
        List<ChildAlertDto> childrenList = result.keySet().stream().findFirst().orElse(Collections.emptyList());
        assertEquals(1, childrenList.size());
        assertEquals("Sarah", childrenList.getFirst().getFirstName()); // Vérifier que l'enfant est Sarah
    }

    @Test
    public void findAllPersonAndMedicalByAdressTest() throws IOException {
        // Arrange
        List<Person> persons = getPerson();
        List<MedicalRecord> medicalRecordList = getMedicalRecord();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(persons);
        when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        // Act
        List<PersonAndMedicalRecordDto> result = personInfoService.findAllPersonAndMedicalByAdress("5 av lyon");
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
    public void findAllPersonGroupedByAddressTest() throws IOException {
        // Arrange
        List<Person> persons = getPerson();
        List<MedicalRecord> medicalRecordList = getMedicalRecord();
        List<FireStation> fireStationList = getFireStation();
        when(dataJsonService.readFileJson(anyString())).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(persons);
        when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
        // Act
        Map<String, List<PersonAndMedicalRecordDto>> result = personInfoService.findAllPersonGroupedByAddress("1");
        assertNotNull(result);
        Set<String> addressList = result.keySet();
        // Assert
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
        List<PersonInfos> result = personInfoService.findAllPersonInfos("Jean");
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
        List<String> listMail = personInfoService.findAllListMailPersonByCity("Nice");
        // Assert
        assertThat(listMail).hasSize(1);
        assertThat(listMail.getFirst()).isEqualTo("sarah@mail.com");

    }
}