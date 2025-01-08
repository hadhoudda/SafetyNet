package com.safetynet.api.controller;

import com.safetynet.api.dto.PersonListByFireStationWithCountDto;
import com.safetynet.api.service.contracts.IPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.safetynet.api.constants.Path.FILE_PATH;

@RestController
public class PersonCoveredByFireStationController {

    @Autowired
    IPersonInfoService iPersonInfoService;
    String pathFile = FILE_PATH;

    //1- Return a list of people covered by the corresponding firestation and must provide a counting the number of adults and the number of children
    @GetMapping(value = "/firestation")
    public ResponseEntity<PersonListByFireStationWithCountDto> getPersonListByFireStationWithCount(@RequestParam String stationNumber) throws IOException {
        PersonListByFireStationWithCountDto personList = iPersonInfoService.findAllPersonListByFireStationWithCount(stationNumber, pathFile);

        if (personList != null) {
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}