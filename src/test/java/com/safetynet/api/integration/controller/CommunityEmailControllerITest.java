package com.safetynet.api.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.CommunityEmailController;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailControllerITest {

    @Autowired
    MockMvc mockMvc;
    @Mock
    private IPersonInfoService personInfoService;
    @InjectMocks
    CommunityEmailController communityEmailController;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(communityEmailController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getListMailPersonByCityTest_Success() throws Exception {
        // Arrange
        String city = "Nice";
        List<String> listEmail = List.of("maxime@mail.com");
        when(personInfoService.findAllListMailPersonByCity(city)).thenReturn(listEmail);
        // Act & Assert
        mockMvc.perform(get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listEmail)));
        // Verify
        verify(personInfoService, times(1)).findAllListMailPersonByCity(city);
    }

    @Test
    public void getListMailPersonByCityTest_NotFound() throws Exception {
        // Arrange
        String city = "Unknown Address";
        when(personInfoService.findAllListMailPersonByCity(city)).thenReturn(new ArrayList<>());
        // Act & Assert
        mockMvc.perform(get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isNotFound());
        // Verify
        verify(personInfoService, times(1)).findAllListMailPersonByCity(city);
    }
}