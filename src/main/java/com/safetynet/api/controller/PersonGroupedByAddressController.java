package com.safetynet.api.controller;

import com.safetynet.api.dto.PersonAndMedicalByAddressDto;
import com.safetynet.api.service.contracts.IPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.safetynet.api.constants.Path.FILE_PATH;

@RestController
public class PersonGroupedByAddressController {

    @Autowired
    IPersonInfoService personInfoService ;
    String pathFile = FILE_PATH;

    //5- Return a list of all persons served by the station and group people by address
    @GetMapping(value = "/flood/stations")
    public ResponseEntity<Map<String, List<PersonAndMedicalByAddressDto>>> getAllPersonGroupedByAddress(String stations){
        Map<String, List<PersonAndMedicalByAddressDto>> listPersons = personInfoService.findAllPersonGroupedByAddress(stations, pathFile);

        if (!listPersons.isEmpty()){
            return new ResponseEntity<>(listPersons, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(listPersons, HttpStatus.NOT_FOUND);
        }
    }
}