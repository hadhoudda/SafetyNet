package com.safetynet.api.controller;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PersonController {

    @Autowired
    IPersonService personService;

    // Create person
    @PostMapping("/person")
    public ResponseEntity<String> postPerson(@RequestBody Person newPerson) {
        try {
            personService.addPerson(newPerson);
            return ResponseEntity.status(HttpStatus.CREATED).body("Person added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add person ");
        }
    }

    // update person
    @PatchMapping("/person")
    public ResponseEntity<String> putPerson(@RequestBody Person person) {
        try {
            personService.updatePerson(person);
            return ResponseEntity.status(HttpStatus.CREATED).body("Person updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update person");
        }
    }

    // Delete person
    @DeleteMapping("/person")
    public ResponseEntity<String> deletePerson(@RequestBody Person person){
        try{
            if ((personService.deletePerson(person))){
                return ResponseEntity.status(HttpStatus.OK).body("Person deleted successfully");
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found person");

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete person");
        }
    }
}