package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.contracts.IMedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class MedicalRecordController {
    @Autowired
    IMedicalRecordService medicalRecordService;

    //create medicalRecord
    @PostMapping("/medicalRecord")
    public ResponseEntity<String> getFireStation(@RequestBody MedicalRecord newMedicalRecord) {
        try {
            if (medicalRecordService.addMedicalRecord(newMedicalRecord)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("MedicalRecord added successfully");
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error:person is not exists or medicalRecord exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add medicalRecord ");
        }
    }

    //Update medicalRecord
    @PatchMapping("/medicalRecord")
    public ResponseEntity<String> putFireStation(@RequestBody MedicalRecord newMedicalRecord) {
        try {
            if (medicalRecordService.updateMedicalRecord(newMedicalRecord)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("MedicalRecord updated successfully");
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: medicalRecord is not exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update medicalRecord ");
        }
    }

    //Delete medicalRecord
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<String> deleteFireStation(@RequestBody MedicalRecord newMedicalRecord) {
        try {
            if (medicalRecordService.deleteMedicalRecord(newMedicalRecord)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("MedicalRecord deleted successfully");
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: medicalRecord is not exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete medicalRecord ");
        }
    }
}