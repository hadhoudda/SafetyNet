package com.safetynet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.dto.PersonAndMedicalRecordDto;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IPersonInfoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PersonAndMedicalRecordContollerTest {
    @InjectMocks
    PersonAndMedicalRecordContoller personAndMedicalRecordContoller;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private IPersonInfoService personInfoService;
    private ObjectMapper objectMapper;

    private List<PersonAndMedicalRecordDto> getPersonAndMedicalRecordDto(){
        List<String> medications1 = new ArrayList<>(List.of("tradoxidine:400mg"));
        List<String> allergies1 = new ArrayList<>(List.of("nillacilan"));
        List<String> medications2 = new ArrayList<>(List.of("noxidian:100mg", "pharmacol:2500mg"));
        List<String> allergies2 = new ArrayList<>(List.of("illisoxian"));
        PersonAndMedicalRecordDto person1 = new PersonAndMedicalRecordDto("Alice", "John", "235648", "Paris", 12, medications1, allergies1);
        PersonAndMedicalRecordDto person2 = new PersonAndMedicalRecordDto("Maxime", "Krys", "569874", "Nice", 56, medications2, allergies2);
        return new ArrayList<>(List.of(person1, person2));
    }

    @BeforeEach
    private void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personAndMedicalRecordContoller).build();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    private void tearDown() {
    }

    @Test
    public void getListPersonAndMedicalByAdressTest_Success() throws Exception {
        // Arrange
        String address = "123 Main St";
        List<PersonAndMedicalRecordDto> result = getPersonAndMedicalRecordDto();
        when(personInfoService.findAllPersonAndMedicalByAdress(address)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/fire")
                        .param("address", address))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
        // Verify
        verify(personInfoService, times(1)).findAllPersonAndMedicalByAdress(address);
    }

    @Test
    public void getListPersonAndMedicalByAdressTest_NotFound() throws Exception {
        // Arrange
        String address = "Unknown Address";
        when(personInfoService.findAllPersonAndMedicalByAdress(address)).thenReturn(new ArrayList<>());
        // Act & Assert
        mockMvc.perform(get("/fire")
                        .param("address", address))
                .andExpect(status().isNotFound());
        // Verify
        verify(personInfoService, times(1)).findAllPersonAndMedicalByAdress(address);
    }
}