package com.safetynet.api.unitaire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.PersonController;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IPersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.safetynet.api.constants.Path.FILE_PATH;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    private IPersonService personService;

    @InjectMocks
    private PersonController personController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Person person = new Person("Alice", "Jean", "5 av lyon", "Paris", "123", "235648", "alice@mail.com");

    private String pathFile = FILE_PATH;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void postPersonTest_Success() throws Exception {
        // Arrange
       when(personService.addPerson(person, pathFile)).thenReturn(true);
        // Act & Assert
        mockMvc.perform(post("/person")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Person added successfully"));
        // Verify
        verify(personService, times(1)).addPerson(person, pathFile);
    }

    @Test
    public void postPersonTest_PersonExists() throws Exception {
        // Arrange
        when(personService.addPerson(person, pathFile)).thenReturn(false);
        // Act & Assert
        mockMvc.perform(post("/person")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Person exists"));
        // Verify
        verify(personService, times(1)).addPerson(person, pathFile);
    }

    @Test
    public void putPersonTest_Success() throws Exception {
        // Arrange
        when(personService.updatePerson(person, pathFile)).thenReturn(true);
        // Act & Assert
        mockMvc.perform(patch("/person")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Person updated successfully"));
        // Verify
        verify(personService, times(1)).updatePerson(person, pathFile);
    }

    @Test
    public void putPersonTest_PersonDoesNotExists() throws Exception {
        // Arrange
        when(personService.updatePerson(person, pathFile)).thenReturn(false);
        // Act & Assert
        mockMvc.perform(patch("/person")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Not found person"));
        // Verify
        verify(personService, times(1)).updatePerson(person, pathFile);
    }

    @Test
    public void deletePersonTest_Success() throws Exception {
        // Arrange
        when(personService.deletePerson(person, pathFile)).thenReturn(true);
        // Act & Assert
        mockMvc.perform(delete("/person")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(content().string("Person deleted successfully"));
        // Verify
        verify(personService, times(1)).deletePerson(person, pathFile);
    }

    @Test
    public void deletePersonTest_PersonDoesNotExists() throws Exception {
        // Arrange
        when(personService.deletePerson(person, pathFile)).thenReturn(false);
        // Act & Assert
        mockMvc.perform(delete("/person")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Not found person"));
        // Verify
        verify(personService, times(1)).deletePerson(person, pathFile);
    }
}
