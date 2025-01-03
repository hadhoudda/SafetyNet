package com.safetynet.api.controller;

import com.safetynet.api.dto.PersonAndMedicalRecordDto;
import com.safetynet.api.service.contracts.IPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonAndMedicalRecordContoller {

    @Autowired
    IPersonInfoService iPersonInfoService;

    // retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne de pompiers
    @GetMapping(value = "/fire")
    public ResponseEntity<List<PersonAndMedicalRecordDto>>  getListPersonAndMedicalByAdress(String address) {
        List<PersonAndMedicalRecordDto> listPersonAndMedicalRecord = iPersonInfoService.findAllPersonAndMedicalByAdress(address);
        if (!listPersonAndMedicalRecord.isEmpty()){
            return  new ResponseEntity<>(listPersonAndMedicalRecord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
