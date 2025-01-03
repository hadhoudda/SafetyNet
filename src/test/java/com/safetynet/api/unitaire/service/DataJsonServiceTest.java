package com.safetynet.api.unitaire.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.service.DataJsonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataJsonServiceTest {
    @InjectMocks
    private DataJsonService dataJsonService;
    @Mock
    DataJsonContainer data;
    @Mock
    private ObjectMapper objectMapper;
    private String pathFile = "src/test/resources/dataTest.json";
    @Mock
    private File file;

    @Test
    public void readFileJsonTest() throws IOException {
        // Arrange
        File file = new File(pathFile);
        when(objectMapper.readValue(file, DataJsonContainer.class)).thenReturn(data);
        // Act
        DataJsonContainer result = dataJsonService.readFileJson(pathFile);
        // Assert
        assertNotNull(result);
        assertEquals(data, result);
    }

}