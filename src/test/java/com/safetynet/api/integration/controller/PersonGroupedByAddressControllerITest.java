package com.safetynet.api.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.PersonGroupedByAddressController;
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
public class PersonGroupedByAddressControllerITest {
    @InjectMocks
    PersonGroupedByAddressController personGroupedByAddressController;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private IPersonInfoService personInfoService;
    private ObjectMapper objectMapper;
    private final String pathFile = FILE_PATH;

    private List<PersonAndMedicalByAddressDto> getPersonAndMedicalRecordDto() {
        List<String> medications1 = new ArrayList<>(List.of("tradoxidine:400mg"));
        List<String> allergies1 = new ArrayList<>(List.of("nillacilan"));
        List<String> medications2 = new ArrayList<>(List.of("noxidian:100mg", "pharmacol:2500mg"));
        List<String> allergies2 = new ArrayList<>(List.of("illisoxian"));
        PersonAndMedicalByAddressDto person1 = new PersonAndMedicalByAddressDto("Alice", "John", "235648", 12, medications1, allergies1);
        PersonAndMedicalByAddressDto person2 = new PersonAndMedicalByAddressDto("Maxime", "Krys", "569874", 56, medications2, allergies2);
        return new ArrayList<>(List.of(person1, person2));
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personGroupedByAddressController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllPersonGroupedByAddressTest_Success() throws Exception {
        //Arrange
        List<String> station = new ArrayList<>(List.of("1","2"));
        String address = "3 place renoir";
        List<PersonAndMedicalByAddressDto> personList = getPersonAndMedicalRecordDto();
        Map<String, List<PersonAndMedicalByAddressDto>> result = new HashMap<>(Map.of(address, personList));
        when(personInfoService.findAllPersonGroupedByAddress(station, pathFile)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1")
                        .param("stations", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
        // Verify
        verify(personInfoService, times(1)).findAllPersonGroupedByAddress(station, pathFile);
    }

    @Test
    public void getAllPersonGroupedByAddressTest_NotFound() throws Exception {
        //Arrange
        List<String> station = new ArrayList<>(List.of("Unknown station"));
        Map<String, List<PersonAndMedicalByAddressDto>> result = new HashMap<>();
        when(personInfoService.findAllPersonGroupedByAddress(station, pathFile)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "Unknown station"))
                .andExpect(status().isNotFound());
        // Verify
        verify(personInfoService, times(1)).findAllPersonGroupedByAddress(station, pathFile);
    }
}