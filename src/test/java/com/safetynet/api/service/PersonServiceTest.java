package com.safetynet.api.service;

import com.safetynet.api.container.DataJsonContainer;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.safetynet.api.constants.Path.FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private DataJsonContainer dataJsonContainer;
    @Mock
    private IDataJsonService dataJsonService;
    @InjectMocks
    private PersonService personService;
    private Person person = new Person("Alice", "Jean", "5 av lyon", "Paris", "123", "235648", "alice@mail.com");
    private List<Person> personList = new ArrayList<>();
    private String pathFile = FILE_PATH;

    @BeforeEach
    private void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void existPersonTest() throws IOException {
        //Arrange
        List<Person> personList = Arrays.asList(person);
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        System.out.println(personList);
        //Act
        boolean result = personService.existPerson(person);
        //Assert
        assertTrue(result);
    }

    @Test
    public void addPersonTest() throws IOException {
        // Arrange
        when(dataJsonService.readFileJson(FILE_PATH)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        //when(personService.existPerson(person)).thenReturn(false);
        doNothing().when(dataJsonService).writeFileJson(dataJsonContainer, pathFile);
        // Act
        boolean result = personService.addPerson(person);
        System.out.println(personList);
        // Assert
        assertTrue(result);
        assertEquals(1, personList.size());
        assertTrue(personList.contains(person));
    }

    @Test
    void updatePersonTest() throws IOException {
        // Arrange
        List<Person> personList = new ArrayList<>(Arrays.asList(person));
        lenient().when((dataJsonService.readFileJson(FILE_PATH))).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
       // when(personService.existPerson(person)).thenReturn(true);
        doNothing().when(dataJsonService).writeFileJson(dataJsonContainer, pathFile);
        // Act
        boolean result = personService.updatePerson(new Person("Alice", "Jean", "26 place europe", "Nice", "123", "235648", "alice@mail.com"));
        // Assert
        assertTrue(result);
    }

    @Test
    void deletePersonTest() throws IOException {
        // Arrange
        List<Person> personList = new ArrayList<>(Arrays.asList(person));
        when(dataJsonService.readFileJson(FILE_PATH)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        doNothing().when(dataJsonService).writeFileJson(dataJsonContainer, pathFile);
        // Act
        boolean result = personService.deletePerson(person);
        // Assert
        assertTrue(result);
        assertFalse(personList.contains(person));
        //Verify
        verify(dataJsonService, times(1)).writeFileJson(dataJsonContainer, pathFile);
    }
}