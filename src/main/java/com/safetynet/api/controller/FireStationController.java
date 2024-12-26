package com.safetynet.api.controller;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.contracts.IFireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationController {

    @Autowired
    IFireStationService fireStationService;

    //create fireStation
    @PostMapping("/firestation")
    public ResponseEntity<String> getFireStation(@RequestBody FireStation newFireStation){
        try {
            if (fireStationService.addFireStation(newFireStation)){
                return ResponseEntity.status(HttpStatus.CREATED).body("FireStation added successfully");
            }else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: fireStation exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add fireStation ");
        }
    }

    //Update fireStation
    @PatchMapping("/firestation")
    public ResponseEntity<String> putFireStation(@RequestBody FireStation newFireStation){
        try {
            if (fireStationService.updateFireStation(newFireStation)){
                return ResponseEntity.status(HttpStatus.CREATED).body("FireStation updated successfully");
            }else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: fireStation is not exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update fireStation ");
        }
    }

    //Delete FireStation
    @DeleteMapping("/firestation")
    public ResponseEntity<String> deleteFireStation(@RequestBody FireStation newFireStation){
        try {
            if (fireStationService.deleteFireStation(newFireStation)){
                return ResponseEntity.status(HttpStatus.CREATED).body("FireStation deleted successfully");
            }else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: fireStation is not exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete fireStation ");
        }
    }

}
