package com.safetynet.api.unitaire.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.controller.MedicalRecordController;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.contracts.IMedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.safetynet.api.constants.Path.FILE_PATH;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {

    @Mock
    private IMedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private final String pathFile = FILE_PATH;
    private List<String> medications = Arrays.asList("ibupurin:200mg", "hydrapermazol:400mg");
    private List<String> allergies = Arrays.asList("nillacilan");
    private MedicalRecord medicalRecord = new MedicalRecord("Nicola", "Leno", "12/06/1975", medications, allergies);

    @BeforeEach
    private void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(medicalRecordController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    public void postMedicalRecordTest_Success() throws Exception {
        //Arrange
        when(medicalRecordService.addMedicalRecord(medicalRecord, pathFile)).thenReturn(true);
        // Act & Assert
        mockMvc.perform(post("/medicalRecord")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medicalRecord)))
                .andExpect(status().isCreated())
                .andExpect(content().string("MedicalRecord added successfully"));
        //Verify
        verify(medicalRecordService, times(1)).addMedicalRecord(medicalRecord, pathFile);
    }

    @Test
    public void postMedicalRecordTest_MedicalRecordDoesNotExists() throws Exception {
        // Arrange
        when(medicalRecordService.addMedicalRecord(medicalRecord, pathFile)).thenReturn(false);
        // Act & Assert
        mockMvc.perform(post("/medicalRecord")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error:person is not exists or medicalRecord exists"));
        // Verify
        verify(medicalRecordService, times(1)).addMedicalRecord(medicalRecord, pathFile);
    }

    @Test
    public void putMedicalRecordTest_Success() throws Exception {
        //Arrange
        when(medicalRecordService.updateMedicalRecord(medicalRecord, pathFile )).thenReturn(true);
        // Act & Assert
        mockMvc.perform(patch("/medicalRecord")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medicalRecord)))
                .andExpect(status().isCreated())
                .andExpect(content().string("MedicalRecord updated successfully"));
        //Verify
        verify(medicalRecordService, times(1)).updateMedicalRecord(medicalRecord, pathFile);
    }

    @Test
    public void putMedicalRecordTest_MedicalRecordDoesNotExists() throws Exception {
        // Arrange
        when(medicalRecordService.updateMedicalRecord(medicalRecord, pathFile)).thenReturn(false);
        // Act & Assert
        mockMvc.perform(patch("/medicalRecord")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: medicalRecord is not exists"));
        // Verify
        verify(medicalRecordService, times(1)).updateMedicalRecord(medicalRecord, pathFile);
    }

    @Test
    public void deleteMedicalRecordTest_Success() throws Exception {
        //Arrange
        when(medicalRecordService.deleteMedicalRecord(medicalRecord, pathFile)).thenReturn(true);
        // Act & Assert
        mockMvc.perform(delete("/medicalRecord")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medicalRecord)))
                .andExpect(status().isOk())
                .andExpect(content().string("MedicalRecord deleted successfully"));
        //Verify
        verify(medicalRecordService, times(1)).deleteMedicalRecord(medicalRecord, pathFile);
    }

    @Test
    public void deleteMedicalRecordTest_MedicalRecordDoesNotExists() throws Exception {
        // Arrange
        when(medicalRecordService.deleteMedicalRecord(medicalRecord, pathFile)).thenReturn(false);
        // Act & Assert
        mockMvc.perform(delete("/medicalRecord")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: medicalRecord is not exists"));
        // Verify
        verify(medicalRecordService, times(1)).deleteMedicalRecord(medicalRecord, pathFile);
    }
}