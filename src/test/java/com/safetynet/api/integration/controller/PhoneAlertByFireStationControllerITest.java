package com.safetynet.api.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.PhoneAlertByFireStationController;
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
public class PhoneAlertByFireStationControllerITest {
    @InjectMocks
    PhoneAlertByFireStationController phoneAlertByFireStationController;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private IPersonInfoService personInfoService;
    private ObjectMapper objectMapper;
    private final String pathFile = FILE_PATH;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(phoneAlertByFireStationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getListPhonePersonByFireStationTest_Success() throws Exception {
        // Arrange
        String firestation = "1";
        List<String> result = new ArrayList<>(List.of("0688888888", "0644444444"));
        when(personInfoService.findAllListPhonePersonByFireStation(firestation, pathFile)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", firestation))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
        // Verify
        verify(personInfoService, times(1)).findAllListPhonePersonByFireStation(firestation, pathFile);
    }

    @Test
    public void getListPhonePersonByFireStationTest_NotFound() throws Exception {
        // Arrange
        String firestation = "1";
        List<String> result = new ArrayList<>();
        when(personInfoService.findAllListPhonePersonByFireStation(firestation, pathFile)).thenReturn(result);
        // Act & Assert
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", firestation))
                .andExpect(status().isNotFound());
        // Verify
        verify(personInfoService, times(1)).findAllListPhonePersonByFireStation(firestation, pathFile);
    }
}