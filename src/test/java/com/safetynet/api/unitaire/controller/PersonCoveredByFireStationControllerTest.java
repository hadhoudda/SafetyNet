package com.safetynet.api.unitaire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.PersonCoveredByFireStationController;
import com.safetynet.api.dto.PersonListByFireStationWithCountDto;
import com.safetynet.api.dto.PersonSimplifiedDto;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PersonCoveredByFireStationControllerTest {
    @InjectMocks
    PersonCoveredByFireStationController personCoveredByFireStationController;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private IPersonInfoService personInfoService;
    private ObjectMapper objectMapper;

    private PersonListByFireStationWithCountDto getPersonListByFireStationWithCountDto(){
        PersonSimplifiedDto person = new PersonSimplifiedDto("Alice", "Jean", "5 av lyon",  "235648");
        List<PersonSimplifiedDto> personList = new ArrayList<>(List.of(person));
        PersonListByFireStationWithCountDto personListByFireStationWithCountDto =  new PersonListByFireStationWithCountDto(personList, 1, 3);
        return personListByFireStationWithCountDto;
    }

    @BeforeEach
    private void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personCoveredByFireStationController).build();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    private void tearDown() {
    }

    @Test
    public void getPersonListByFireStationWithCountTest_Success() throws Exception {
        //Arrange
        String stationNumber = "1";
        PersonListByFireStationWithCountDto result = getPersonListByFireStationWithCountDto();
        when(personInfoService.findAllPersonListByFireStationWithCount(stationNumber)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", stationNumber))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
        // Verify
        verify(personInfoService, times(1)).findAllPersonListByFireStationWithCount(stationNumber);
    }

    @Test
    public void getPersonListByFireStationWithCountTest_NotFound() throws Exception {
        //Arrange
        String stationNumber =  "Unknown stationNumber";
        PersonListByFireStationWithCountDto result =  null;
        when(personInfoService.findAllPersonListByFireStationWithCount(stationNumber)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", stationNumber))
                .andExpect(status().isNotFound());
        // Verify
        verify(personInfoService, times(1)).findAllPersonListByFireStationWithCount(stationNumber);
    }


}