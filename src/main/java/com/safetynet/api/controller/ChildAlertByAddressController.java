package com.safetynet.api.controller;

import com.safetynet.api.dto.ChildAlertDto;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.ServicePersonService;
import com.safetynet.api.service.contracts.IServicePersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChildAlertByAddressController {
    IServicePersonService iServicePersonService = new ServicePersonService();

    //2 -retourner une liste d'enfants et une liste des autres membres du foyer
    @GetMapping(value = "/childAlert")
    public ResponseEntity<Map<List<ChildAlertDto>, List<Person>>> getListChild(@RequestParam String address) {
        Map<List<ChildAlertDto>, List<Person>> listMap =iServicePersonService.findAllChildByAdress(address);

        if(!listMap.isEmpty()){
            return new ResponseEntity<>(listMap, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
}