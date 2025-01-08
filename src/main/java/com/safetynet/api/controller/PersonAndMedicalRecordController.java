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
public class PersonAndMedicalRecordController {

    @Autowired
    IPersonInfoService iPersonInfoService;
    String pathFile = FILE_PATH;

    //4- Return the list of persons living at the given address as well as the firestation number
    @GetMapping(value = "/fire")
    public ResponseEntity<Map<String, List<PersonAndMedicalByAddressDto>>> getListPersonAndMedicalByAddress(String address) {
        Map<String, List<PersonAndMedicalByAddressDto>> listPersonAndMedicalByAddress = iPersonInfoService.findAllPersonAndMedicalByAddress(address, pathFile);

        if (!listPersonAndMedicalByAddress.isEmpty()){
            return  new ResponseEntity<>(listPersonAndMedicalByAddress, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(listPersonAndMedicalByAddress,HttpStatus.NOT_FOUND);
        }
    }
}