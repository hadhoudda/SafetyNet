package com.safetynet.api.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IDataJsonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static com.safetynet.api.constants.Path.FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
    @Mock
    private File file;


    private Person person1 = new Person("Alice", "Jean", "5 av lyon", "Paris", "123", "235648", "alice@mail.com");
    private Person person2 = new Person("Sarah", "Krys", "12 place Monplier", "Nice", "457", "543896", "sarah@mail.com");
    //private Person person3 = new Person("Maxime", "Rock", "5 av lyon", "Paris", "123", "489575", "maxime@mail.com");
    private FireStation fireStation1 = new FireStation("5 av lyon","1");
    private FireStation fireStation2 = new FireStation("12 place Monplier","2");
    private List<Person> personList = new ArrayList<>();
    private List<FireStationService> fireStationServiceList = new ArrayList<>();


    @BeforeEach
    private void setUp() {
    }

    @AfterEach
    private void tearDown() {
    }


//    @Test
//    public void filtrePersonByFirestationTest() throws IOException {
//        //Arrange
//        List<Person> personList = Arrays.asList(person1,person2);
//        List<FireStation> fireStationList = Arrays.asList(fireStation1,fireStation2);
//        String filtre = "5 av lyon";
//        when(dataJsonService.readFileJson(FILE_PATH)).thenReturn(dataJsonContainer);
//        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
//        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
//        // Act
//        List<Person> result = personInfoService.filtrePersonByFirestation(filtre);
//        System.out.println(result);
//        verify(dataJsonService).readFileJson(FILE_PATH);
//        verify(dataJsonContainer).getPersonsList();
//        verify(dataJsonContainer).getFireStationList();
//        System.out.println(personList);
//        System.out.println(fireStationList);
//        // Assert
//     assertEquals(1, result.size());
////        assertTrue(result.contains(person1));
////        assertFalse(result.contains(person2));
//    }
//
//

    @Test
    public void findAllPersonListByFireStationWithCountTest() {
    }

    @Test
    public void findAllListPhonePersonByFireStationTest() {
    }

    @Test
    public void findAllChildByAdressTest() {
    }

    @Test
    public void findAllPersonAndMedicalByAdressTest() {
    }

    @Test
    public void findAllPersonGroupedByAddressTest() {
    }

    @Test
    public void findAllPersonInfosTest() {
    }

    @Test
    public void findAllListMailPersonByCityTest() {
    }
}