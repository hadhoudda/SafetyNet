package com.safetynet.api.integration.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.DataJsonService;
import com.safetynet.api.service.contracts.IPersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonServiceITest {
    @Autowired
    DataJsonService dataJsonService;
    @Autowired
    IPersonService personService;
    private final String pathFile = "src/test/resources/dataTest.json";
    private final Person person = new Person("Alice", "Jean", "5 av lyon", "Paris", "123", "235648", "alice@mail.com");


    @Test
    public void existPersonITest() {
        try {
            DataJsonContainer dataJsonContainer = dataJsonService.readFileJson(pathFile);
            List<Person> personList = dataJsonContainer.getPersonsList();
            System.out.println(personList);
            boolean result = personService.existPerson(person, pathFile);
            assertFalse(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add_Update_DeletedPersonITest() {
        try {
            existPersonITest();
            boolean personAdded = personService.addPerson(person, pathFile);
            boolean personUpdated = personService.updatePerson(person, pathFile);
            boolean personDeleted = personService.deletePerson(person, pathFile);
            assertTrue(personAdded);
            assertTrue(personUpdated);
            assertTrue(personDeleted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}