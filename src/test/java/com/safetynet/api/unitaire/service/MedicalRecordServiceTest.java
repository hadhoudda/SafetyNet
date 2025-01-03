package com.safetynet.api.unitaire.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.MedicalRecordService;
import com.safetynet.api.service.contracts.IDataJsonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.safetynet.api.constants.Path.FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
    @InjectMocks
    MedicalRecordService medicalRecordService;
    @Mock
    IDataJsonService dataJsonService;
    @Mock
    DataJsonContainer dataJsonContainer;
    private final Person person = new Person("Nicola", "Leon", "5 avenue Renoir", "Paris", "123", "235648", "leon@mail.com");
    private final String pathFile = FILE_PATH;

    private MedicalRecord getMedicalRecord() {
        List<String> medications = Arrays.asList("ibupurin:200mg");
        List<String> allergies = Arrays.asList("nillacilan");
        MedicalRecord medicalRecord = new MedicalRecord("Nicola", "Leon", "12/06/1975", medications, allergies);
        return medicalRecord;
    }
    private final MedicalRecord medicalRecord = getMedicalRecord();
    @Test
    public void existsMedicalRecordTest() throws IOException {
        List<MedicalRecord> medicalRecordList = new ArrayList<>(List.of(medicalRecord));
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        lenient().when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        System.out.println(medicalRecordList);
        medicalRecordService.existsMedicalRecord(medicalRecord, pathFile);
        assertEquals(medicalRecordList.getFirst(), medicalRecord);
    }

    @Test
    public void existsPersonByMedicalRecordTest() throws IOException {
        List<Person> personList = new ArrayList<>(List.of(person));
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        lenient().when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        System.out.println(personList);
        medicalRecordService.existPersonByMedicalRecord(medicalRecord, pathFile);
        assertEquals(personList.getFirst(), person);
    }

    @Test
    public void addMedicalRecordTest() throws IOException {
        List<Person> personList = new ArrayList<>(List.of(person));
        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        lenient().when(dataJsonContainer.getPersonsList()).thenReturn(personList);
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        doNothing().when(dataJsonService).writeFileJson(dataJsonContainer, pathFile);
        boolean result = medicalRecordService.addMedicalRecord(medicalRecord, pathFile);
        assertTrue(result);
        assertEquals(1, medicalRecordList.size());
        assertTrue(medicalRecordList.contains(medicalRecord));
    }

    @Test
    public void updateMedicalRecordTest() throws IOException {
        List<MedicalRecord> medicalRecordList = new ArrayList<>(List.of(medicalRecord));
        List<String> medications = Arrays.asList("ibupurin:200mg", "hydrapermazol:400mg");
        List<String> allergies = List.of("poulaine");
        MedicalRecord medicalRecordUpdate = new MedicalRecord("Nicola", "Leon", "12/06/1975", medications, allergies);
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        doNothing().when(dataJsonService).writeFileJson(dataJsonContainer, pathFile);
        boolean result = medicalRecordService.updateMedicalRecord(medicalRecordUpdate, pathFile);
        assertTrue(result);
        assertEquals(1, medicalRecordList.size());
        assertTrue(medicalRecordList.contains(medicalRecordUpdate));
    }

    @Test
    public void deleteMedicalRecordTest() throws IOException {
        List<MedicalRecord> medicalRecordList = new ArrayList<>(List.of(medicalRecord));
        when(dataJsonService.readFileJson(pathFile)).thenReturn(dataJsonContainer);
        when(dataJsonContainer.getMedicalRecordList()).thenReturn(medicalRecordList);
        doNothing().when(dataJsonService).writeFileJson(dataJsonContainer, pathFile);
        boolean result = medicalRecordService.deleteMedicalRecord(medicalRecord, pathFile);
        assertTrue(result);
        assertEquals(0, medicalRecordList.size());
        assertFalse(medicalRecordList.contains(medicalRecord));
    }
}