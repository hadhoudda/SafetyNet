package com.safetynet.api.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.PersonAndMedicalRecordController;
import com.safetynet.api.dto.PersonAndMedicalByAddressDto;
import com.safetynet.api.service.contracts.IPersonInfoService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.safetynet.api.constants.Path.FILE_PATH;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PersonAndMedicalRecordControllerITest {
    @InjectMocks
    PersonAndMedicalRecordController personAndMedicalRecordController;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private IPersonInfoService personInfoService;
    private ObjectMapper objectMapper;
    private final String pathFile = FILE_PATH;

    private Map<String, List<PersonAndMedicalByAddressDto>> getPersonAndMedicalRecordDto() {
        List<String> medications1 = new ArrayList<>(List.of("tradoxidine:400mg"));
        List<String> allergies1 = new ArrayList<>(List.of("nillacilan"));
        List<String> medications2 = new ArrayList<>(List.of("noxidian:100mg", "pharmacol:2500mg"));
        List<String> allergies2 = new ArrayList<>(List.of("illisoxian"));
        PersonAndMedicalByAddressDto person1 = new PersonAndMedicalByAddressDto("Alice", "John", "235648", 12, medications1, allergies1);
        PersonAndMedicalByAddressDto person2 = new PersonAndMedicalByAddressDto("Maxime", "Krys", "569874", 56, medications2, allergies2);
        List<PersonAndMedicalByAddressDto> list = new ArrayList<>(List.of(person1, person2));
        Map<String, List<PersonAndMedicalByAddressDto>> medicalRecordsMap = new HashMap<>();
        medicalRecordsMap.put("1", list);
        return medicalRecordsMap;
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personAndMedicalRecordController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getListPersonAndMedicalByAddressTest_Success() throws Exception {
        // Arrange
        String address = "123 Main St";
        Map<String, List<PersonAndMedicalByAddressDto>> result = getPersonAndMedicalRecordDto();
        when(personInfoService.findAllPersonAndMedicalByAddress(address, pathFile)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/fire")
                        .param("address", address))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
        // Verify
        verify(personInfoService, times(1)).findAllPersonAndMedicalByAddress(address, pathFile);
    }

    @Test
    public void getListPersonAndMedicalByAddressTest_NotFound() throws Exception {
        // Arrange
        String address = "Unknown Address";
        when(personInfoService.findAllPersonAndMedicalByAddress(address, pathFile)).thenReturn(new HashMap<>());
        // Act & Assert
        mockMvc.perform(get("/fire")
                        .param("address", address))
                .andExpect(status().isNotFound());
        // Verify
        verify(personInfoService, times(1)).findAllPersonAndMedicalByAddress(address, pathFile);
    }
}