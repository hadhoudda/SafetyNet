package com.safetynet.api.controller;


import com.safetynet.api.dto.PersonAndMedicalRecordDto;
import com.safetynet.api.service.contracts.IPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PersonGroupedByAddressController {

    @Autowired
    IPersonInfoService personInfoService ;

    @GetMapping(value = "/flood/stations")
    public ResponseEntity<Map<String, List<PersonAndMedicalRecordDto>>> getAllPersonGroupedByAddress(String stations){
        Map<String, List<PersonAndMedicalRecordDto>> listPersons = personInfoService.findAllPersonGroupedByAddress(stations);

        if (!listPersons.isEmpty()){
            return new ResponseEntity<>(listPersons, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
