package com.safetynet.api.integration.service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.contracts.IMedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordServiceITest {
    @Autowired
    IMedicalRecordService medicalRecordService;
    private final String pathFile = "src/test/resources/dataTest.json";

    private MedicalRecord getMedicalRecord() {
        List<String> medications = Arrays.asList("ibupurin:200mg");
        List<String> allergies = Arrays.asList("nillacilan");
        MedicalRecord medicalRecord = new MedicalRecord("Nicola", "Krys", "12/06/1975", medications, allergies);
        return medicalRecord;
    }

    MedicalRecord medicalRecord = getMedicalRecord();

    @Test
    public void existsPersonByMedicalRecordTest() {

        try {
            boolean result = medicalRecordService.existPersonByMedicalRecord(medicalRecord, pathFile);
            assertTrue(result);//test person exists

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void existsMedicalRecordITest() {
        try {
            boolean result = medicalRecordService.existsMedicalRecord(medicalRecord, pathFile);
            assertFalse(result);//medicalRecord is not exists
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add_Update_DeletedMedicalRecordITest() {
        try {
            existsPersonByMedicalRecordTest();
            existsMedicalRecordITest();
            boolean medicalRecordAdded = medicalRecordService.addMedicalRecord(medicalRecord, pathFile);
            boolean medicalRecordUpdated = medicalRecordService.updateMedicalRecord(medicalRecord, pathFile);
            boolean medicalRecordDeleted = medicalRecordService.deleteMedicalRecord(medicalRecord, pathFile);
            assertTrue(medicalRecordAdded);
            assertTrue(medicalRecordUpdated);
            assertTrue(medicalRecordDeleted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

