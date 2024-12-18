package com.safetynet.api.controller;

import com.safetynet.api.service.ServicePersonService;
import com.safetynet.api.service.contracts.IServicePersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneAlertByFireStation {

    IServicePersonService iServicePersonService = new ServicePersonService();

    // 3- retourner une liste des numeros de telephone des persons couvertes par la caserne de pompiers correspondante
    @GetMapping(value = "/phoneAlert")
    public ResponseEntity<List<String>> getListPhonePersonByFireStation(@RequestParam String firestation) {

        List<String> listPhone = iServicePersonService.findAllListPhonePersonByFireStation(firestation);

        if (!listPhone.isEmpty()) {
            return new ResponseEntity<>(listPhone, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}