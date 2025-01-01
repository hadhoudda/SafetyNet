package com.safetynet.api.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.FireStation;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {
    @InjectMocks
    private FireStationService fireStationService;
    @Mock
    private IDataJsonService dataJsonService;
    @Mock
    private DataJsonContainer dataJsonContainer;
    private String pathFile = FILE_PATH;
    FireStation fireStation = new FireStation("5 place renoir", "1");

    @Test
    public void existsFireStationTest() throws IOException {
        List<FireStation> fireStationList = List.of(fireStation);
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
        boolean result = fireStationService.existsFireStation(fireStation);
        System.out.println(fireStationList);
        assertTrue(result);
    }

    @Test
    public void addFireStation() throws IOException {
        List<FireStation> fireStationList = new ArrayList<>();
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
        boolean result = fireStationService.addFireStation(fireStation);
        assertTrue(result);
        assertEquals(1, fireStationList.size());
        assertTrue(fireStationList.contains(fireStation));
    }

    @Test
    public void updateFireStation() throws IOException {
        List<FireStation> fireStationList = new ArrayList<>(List.of(fireStation));
        lenient().when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
        lenient().doNothing().when(dataJsonService).writeFileJson(dataJsonContainer, pathFile);
        boolean result = fireStationService.updateFireStation(new FireStation("5 place renoir", "3"));
        assertTrue(result);
        assertEquals(1, fireStationList.size());
    }

    @Test
    public void deleteFireStation() throws IOException {
        List<FireStation> fireStationList = new ArrayList<>(List.of(fireStation));
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getFireStationList()).thenReturn(fireStationList);
        boolean result = fireStationService.deleteFireStation(fireStation);
        assertTrue(result);
        assertEquals(0, fireStationList.size());
        assertFalse(fireStationList.contains(fireStation));
    }
}