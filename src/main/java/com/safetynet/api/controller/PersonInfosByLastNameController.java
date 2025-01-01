package com.safetynet.api.controller;

import com.safetynet.api.dto.PersonInfos;
import com.safetynet.api.service.PersonInfoService;
import com.safetynet.api.service.contracts.IPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonInfosByLastNameController {

    @Autowired
    IPersonInfoService iPersonInfoService;

    @GetMapping(value = "/personInfolastName={lastName}")
    public ResponseEntity<List<PersonInfos>> getPersonInfoByLastName(@PathVariable String lastName){
        List<PersonInfos> personInfosList = iPersonInfoService.findAllPersonInfos(lastName);

        if (!personInfosList.isEmpty()){
            return new ResponseEntity<>(personInfosList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}