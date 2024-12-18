package com.safetynet.api.controller;

import com.safetynet.api.dto.PersonListByFireStationWithCountDto;
import com.safetynet.api.service.ServicePersonService;
import com.safetynet.api.service.contracts.IServicePersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PersonCoveredByFireStationController {

    IServicePersonService iServicePersonService = new ServicePersonService();

    // 1- retourner une liste des personnes couvertes par la caserne de pompiers correspondante
    @GetMapping(value = "/firestation")
    public ResponseEntity<PersonListByFireStationWithCountDto> getPersonListByFireStationWithCount(@RequestParam String stationNumber) throws IOException {
        PersonListByFireStationWithCountDto personList = iServicePersonService.findAllPersonListByFireStationWithCount(stationNumber);

        if (personList != null) {
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}