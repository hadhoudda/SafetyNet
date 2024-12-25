package com.safetynet.api.controller;

import com.safetynet.api.service.PersonInfoService;
import com.safetynet.api.service.contracts.IPersonInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommunityEmailController {

    IPersonInfoService iPersonInfoService = new PersonInfoService();

    //7- retourner les adresses mail de tous les habitants de la ville
    @GetMapping(value = "/communityEmail")
    public ResponseEntity<List<String>>  getListMailPersonByCity(@RequestParam String city) {
        List<String> listEmail = iPersonInfoService.findAllListMailPersonByCity(city);

        if(!listEmail.isEmpty()){
            return  new ResponseEntity<>(listEmail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}