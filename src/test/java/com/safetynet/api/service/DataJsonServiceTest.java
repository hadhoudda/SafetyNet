package com.safetynet.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.constants.Path;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataJsonServiceTest {
    @InjectMocks
    private DataJsonService dataJsonService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    DataJsonContainer dataTest;
    private String path = "src/main/resources/data.json";
    @Mock
    private File file;

    @BeforeEach
    private void setUp() {
    }

    @AfterEach
    private void tearDown() {
    }

    @Test
    public void readFileJsonTest() throws IOException {
        // Arrange
        DataJsonContainer data = new DataJsonContainer();
        File file = new File(path);
        when(objectMapper.readValue(file, DataJsonContainer.class)).thenReturn(data);
        // Act
        DataJsonContainer result = dataJsonService.readFileJson(path);
        // Assert
        assertNotNull(result);
        assertEquals(data, result);
    }


//    @Test
//    public void writeFileJsonTest_FileExists() throws IOException {
//        // Arrange
//        DataJsonContainer dataTest = new DataJsonContainer();
//        when(file.exists()).thenReturn(true);
//        //Act
//        dataJsonService.writeFileJson(dataTest);
//        // Verify
//        verify(objectMapper, times(1)).writeValue(file, dataTest);
//        verify(file, times(1)).exists();
//    }

//    @Test
//    public void writeFileJsonTest_FileDoesNotExist() throws IOException {
//        // Arrange
//        DataJsonContainer data = new DataJsonContainer();
//        when(file.exists()).thenReturn(false);
//        when(file.createNewFile()).thenReturn(true);
//        // Acct
//        dataJsonService.writeFileJson(data);
//        // Verify
//        verify(objectMapper, times(1)).writeValue(file, data);
//        verify(file, times(1)).createNewFile();
//    }
}