package com.safetynet.api.controller;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.contracts.IFireStationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import static com.safetynet.api.constants.Path.FILE_PATH;

@RestController
public class FireStationController {

    @Autowired
    IFireStationService fireStationService;
    private final String pathFile = FILE_PATH;

    //Create fireStation
    @PostMapping("/firestation")
    public ResponseEntity<String> postFireStation(@RequestBody @Valid FireStation newFireStation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { //Checking validation errors in data submitted to the model firestation
            StringBuilder errorMessage = new StringBuilder("Error: ");
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }
        try {
            if (fireStationService.addFireStation(newFireStation, pathFile)){
                return ResponseEntity.status(HttpStatus.CREATED).body("FireStation added successfully");
            }else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: FireStation exists");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add fireStation");
        }
    }

    //Update fireStation
    @PatchMapping("/firestation")
    public ResponseEntity<String> putFireStation(@RequestBody @Valid FireStation newFireStation){
        try {
            if (fireStationService.updateFireStation(newFireStation, pathFile)){
                return ResponseEntity.status(HttpStatus.CREATED).body("FireStation updated successfully");
            }else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: fireStation is not exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update fireStation");
        }
    }

    //Delete FireStation
    @DeleteMapping("/firestation")
    public ResponseEntity<String> deleteFireStation(@RequestBody @Valid FireStation newFireStation){
        try {
            if (fireStationService.deleteFireStation(newFireStation, pathFile)){
                return ResponseEntity.status(HttpStatus.OK).body("FireStation deleted successfully");
            }else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: fireStation is not exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete fireStation");
        }
    }
}