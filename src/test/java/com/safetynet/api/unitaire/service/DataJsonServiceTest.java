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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataJsonServiceTest {

    @InjectMocks
    private DataJsonService dataJsonService;
    @Mock
    DataJsonContainer data;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private File file;

    @Test
    public void readFileJsonTest() throws IOException {
        // Arrange
        String pathFile = "src/test/resources/dataTest.json";
        File file = new File(pathFile);
        when(objectMapper.readValue(file, DataJsonContainer.class)).thenReturn(data);
        // Act
        DataJsonContainer result = dataJsonService.readFileJson(pathFile);
        // Assert
        assertNotNull(result);
        assertEquals(data, result);
    }
}