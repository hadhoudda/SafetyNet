package com.safetynet.api.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.PersonInfosByLastNameController;
import com.safetynet.api.dto.PersonInfosDto;
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
import java.util.List;

import static com.safetynet.api.constants.Path.FILE_PATH;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PersonInfosDtoByLastNameControllerITest {
    @InjectMocks
    PersonInfosByLastNameController personInfosByLastNameController;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private IPersonInfoService personInfoService;
    private ObjectMapper objectMapper;
    private final String pathFile = FILE_PATH;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personInfosByLastNameController).build();
        objectMapper = new ObjectMapper();
    }

    private List<PersonInfosDto> getPersonInfosList() {
        List<String> medications1 = new ArrayList<>(List.of("tradoxidine:400mg"));
        List<String> allergies1 = new ArrayList<>(List.of("nillacilan"));
        List<String> medications2 = new ArrayList<>(List.of("noxidian:100mg", "pharmacol:2500mg"));
        List<String> allergies2 = new ArrayList<>(List.of("illisoxian"));
        PersonInfosDto person1 = new PersonInfosDto("Alice", "John", "Paris", 12, "alice@gmail.com", medications1, allergies1);
        PersonInfosDto person2 = new PersonInfosDto("Maxime", "John", "Nice", 56, "maxime@yahoo.fr", medications2, allergies2);
        return new ArrayList<>(List.of(person1, person2));
    }

    @Test
    public void getPersonInfoByLastNameTest_Success() throws Exception {
        String lastName = "John";
        List<PersonInfosDto> result = getPersonInfosList();
        when(personInfoService.findAllPersonInfos(lastName, pathFile)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/personInfolastName={lastName}", lastName))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
        // Verify
        verify(personInfoService, times(1)).findAllPersonInfos(lastName, pathFile);
    }

    @Test
    public void getPersonInfoByLastNameTest_NotFound() throws Exception {
        //Arrange
        String lastName = "Unknown lastName";
        List<PersonInfosDto> result = new ArrayList<>();
        lenient().when(personInfoService.findAllPersonInfos(lastName, pathFile)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/personInfolastName={lastName}", lastName))
                .andExpect(status().isNotFound());
        // Verify
        verify(personInfoService, times(1)).findAllPersonInfos(lastName, pathFile);
    }
}