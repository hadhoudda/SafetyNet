package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.contracts.IMedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.safetynet.api.constants.Path.FILE_PATH;


@RestController
public class MedicalRecordController {
    @Autowired
    IMedicalRecordService medicalRecordService;
    String pathFile = FILE_PATH;

    //create medicalRecord
    @PostMapping("/medicalRecord")
    public ResponseEntity<String> getFireStation(@RequestBody @Valid MedicalRecord newMedicalRecord) {
        try {
            if (medicalRecordService.addMedicalRecord(newMedicalRecord, pathFile)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("MedicalRecord added successfully");
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error:person is not exists or medicalRecord exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add medicalRecord ");
        }
    }

    //Update medicalRecord
    @PatchMapping("/medicalRecord")
    public ResponseEntity<String> putFireStation(@RequestBody @Valid MedicalRecord newMedicalRecord) {
        try {
            if (medicalRecordService.updateMedicalRecord(newMedicalRecord, pathFile)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("MedicalRecord updated successfully");
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: medicalRecord is not exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update medicalRecord ");
        }
    }

    //Delete medicalRecord
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<String> deleteFireStation(@RequestBody @Valid MedicalRecord newMedicalRecord) {
        try {
            if (medicalRecordService.deleteMedicalRecord(newMedicalRecord, pathFile)) {
                return ResponseEntity.status(HttpStatus.OK).body("MedicalRecord deleted successfully");
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: medicalRecord is not exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete medicalRecord ");
        }
    }
}