package com.safetynet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IPersonInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class ChildAlertByAddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IPersonInfoService personInfoService;

    @InjectMocks
    private ChildAlertByAddressController childAlertByAddressController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(childAlertByAddressController).build();
        objectMapper = new ObjectMapper();
    }

    private Map<List<ChildAlertDto>, List<Person>> getListPersonChildren(){
        ChildAlertDto child1 = new ChildAlertDto("John", "Krys", 5);
        ChildAlertDto child2 = new ChildAlertDto("Jane", "Krys", 8);
        List<ChildAlertDto> children = Arrays.asList(child1, child2);
        Person adult1 = new Person("Alice", "Krys", "123 Main St", "Paris", "123", "235648", "alice@mail.com");
        Person adult2 = new Person("Maxime", "Krys", "123 Main St", "Nice", "568", "569874", "maxime@mail.com");
        List<Person> adults = Arrays.asList(adult1, adult2);
        Map<List<ChildAlertDto>, List<Person>> map = new HashMap<>();
        map.put(children, adults);
        return map;
    }

    @Test
    public void getListChildTest_Success() throws Exception {
        // Arrange
        String address = "123 Main St";
        Map<List<ChildAlertDto>, List<Person>> result = getListPersonChildren();
        when(personInfoService.findAllChildByAdress(address)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/childAlert")
                        .param("address", address))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
        // Verify
        verify(personInfoService, times(1)).findAllChildByAdress(address);
    }

    @Test
    public void getListChildTest_NotFound() throws Exception {
        // Arrange
        String address = "Unknown Address";
        when(personInfoService.findAllChildByAdress(address)).thenReturn(new HashMap<>());
        // Act & Assert
        mockMvc.perform(get("/childAlert")
                        .param("address", address))
                .andExpect(status().isNotFound());
        // Verify
        verify(personInfoService, times(1)).findAllChildByAdress(address);
    }
}
