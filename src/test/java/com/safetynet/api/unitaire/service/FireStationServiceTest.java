package com.safetynet.api.unitaire.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.FireStationService;
import com.safetynet.api.service.contracts.IDataJsonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.safetynet.api.constants.Path.FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @InjectMocks
    private FireStationService fireStationService;
    @Mock
    private IDataJsonService dataJsonService;
    @Mock
    private DataJsonContainer dataJsonContainer;
    private final String pathFile = FILE_PATH;
    FireStation fireStation = new FireStation("5 place renoir", "1");

    @Test
    public void existsFireStationTest() throws IOException {
        //Arrange
        List<FireStation> fireStationList = List.of(fireStation);
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
        //Arrange
        boolean result = fireStationService.existsFireStation(fireStation, pathFile);
        System.out.println(fireStationList);
        //Assert
        assertTrue(result);
    }

    @Test
    public void addFireStation() throws IOException {
        //Arrange
        List<FireStation> fireStationList = new ArrayList<>();
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
        //Arrange
        boolean result = fireStationService.addFireStation(fireStation, pathFile);
        //Assert
        assertTrue(result);
        assertEquals(1, fireStationList.size());
        assertTrue(fireStationList.contains(fireStation));
    }

    @Test
    public void updateFireStation() throws IOException {
        //Arrange
        List<FireStation> fireStationList = new ArrayList<>(List.of(fireStation));
        lenient().when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
        lenient().doNothing().when(dataJsonService).writeFileJson(dataJsonContainer, pathFile);
        //Arrange
        boolean result = fireStationService.updateFireStation(new FireStation("5 place renoir", "3"), pathFile);
        //Assert
        assertTrue(result);
        assertEquals(1, fireStationList.size());
    }

    @Test
    public void deleteFireStation() throws IOException {
        //Arrange
        List<FireStation> fireStationList = new ArrayList<>(List.of(fireStation));
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
        //Act
        boolean result = fireStationService.deleteFireStation(fireStation, pathFile);
        //Assert
        assertTrue(result);
        assertEquals(0, fireStationList.size());
        assertFalse(fireStationList.contains(fireStation));
    }
}