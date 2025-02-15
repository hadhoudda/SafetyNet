package com.safetynet.api.controller;

import com.safetynet.api.service.contracts.IPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommunityEmailController {

    @Autowired
    IPersonInfoService iPersonInfoService;

    //7- Return the email of all residents of the city
    @GetMapping(value = "/communityEmail")
    public ResponseEntity<List<String>>  getListMailPersonByCity(@RequestParam String city) {
        List<String> listEmail = iPersonInfoService.findAllListMailPersonByCity(city);

        if(!listEmail.isEmpty()){
            return  new ResponseEntity<>(listEmail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(listEmail,HttpStatus.NOT_FOUND);
        }
    }
}