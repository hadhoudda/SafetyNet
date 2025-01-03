package com.safetynet.api.unitaire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.FireStationController;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.contracts.IFireStationService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FireStationControllerTest {

    @Mock
    private IFireStationService fireStationService;

    @InjectMocks
    private FireStationController fireStationController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private String pathFile = FILE_PATH;
    private FireStation fireStation = new FireStation("5 place marche", "5");

    @BeforeEach
    private void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fireStationController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void postFireStationTest_Success() throws Exception {
        //Arrange
        when(fireStationService.addFireStation(fireStation, pathFile)).thenReturn(true);
        // Act & Assert
        mockMvc.perform(post("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireStation)))
                .andExpect(status().isCreated())
                .andExpect(content().string("FireStation added successfully"));
        //Verify
        verify(fireStationService, times(1)).addFireStation(fireStation, pathFile);
    }

    @Test
    public void postFireStationTest_FireStationExists() throws Exception {
        // Arrange
        when(fireStationService.addFireStation(fireStation, pathFile)).thenReturn(false);
        // Act & Assert
        mockMvc.perform(post("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireStation)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: FireStation exists"));
        // Verify
        verify(fireStationService, times(1)).addFireStation(fireStation, pathFile);
    }

    @Test
    public void putFireStationTest_Success() throws Exception {
        //Arrange
        when(fireStationService.updateFireStation(fireStation, pathFile)).thenReturn(true);
        // Act & Assert
        mockMvc.perform(patch("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireStation)))
                .andExpect(status().isCreated())
                .andExpect(content().string("FireStation updated successfully"));
        //Verify
        verify(fireStationService, times(1)).updateFireStation(fireStation, pathFile);
    }

    @Test
    public void putFireStationTest_FireStationDoesNotExists() throws Exception {
        // Arrange
        when(fireStationService.updateFireStation(fireStation, pathFile)).thenReturn(false);
        // Act & Assert
        mockMvc.perform(patch("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireStation)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: fireStation is not exists"));
        // Verify
        verify(fireStationService, times(1)).updateFireStation(fireStation, pathFile);
    }

    @Test
    public void deleteFireStationTest_Success() throws Exception {
        //Arrange
        when(fireStationService.deleteFireStation(fireStation, pathFile)).thenReturn(true);
        // Act & Assert
        mockMvc.perform(delete("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireStation)))
                .andExpect(status().isOk())
                .andExpect(content().string("FireStation deleted successfully"));
        //Verify
        verify(fireStationService, times(1)).deleteFireStation(fireStation, pathFile);
    }

    @Test
    public void deleteFireStationTest_FireStationDoesNotExists() throws Exception {
        // Arrange
        when(fireStationService.deleteFireStation(fireStation, pathFile)).thenReturn(false);
        // Act & Assert
        mockMvc.perform(delete("/firestation")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireStation)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: fireStation is not exists"));
        // Verify
        verify(fireStationService, times(1)).deleteFireStation(fireStation, pathFile);
    }
}