package com.safetynet.api.controller;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IPersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.safetynet.api.constants.Path.FILE_PATH;

@RestController
public class PersonController {

    @Autowired
    IPersonService personService;

    String pathFile = FILE_PATH;

    // Create person
    @PostMapping("/person")
    public ResponseEntity<String> postPerson(@RequestBody @Valid Person newPerson) {
        try {
            if (personService.addPerson(newPerson, pathFile)){
                return ResponseEntity.status(HttpStatus.CREATED).body("Person added successfully");
            }else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Person exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add person ");
        }
    }

    // update person
    @PatchMapping("/person")
    public ResponseEntity<String> putPerson(@RequestBody @Valid Person person) {
        try {
            if (personService.updatePerson(person, pathFile)){
                return ResponseEntity.status(HttpStatus.CREATED).body("Person updated successfully");
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found person");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update person");
        }
    }

    // Delete person
    @DeleteMapping("/person")
    public ResponseEntity<String> deletePerson(@RequestBody @Valid Person person){
        try{
            if ((personService.deletePerson(person, pathFile))){
                return ResponseEntity.status(HttpStatus.OK).body("Person deleted successfully");
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found person");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete person");
        }
    }
}