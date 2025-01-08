package com.safetynet.api.controller;

import com.safetynet.api.service.contracts.IPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.safetynet.api.constants.Path.FILE_PATH;

@RestController
public class PhoneAlertByFireStationController {

    @Autowired
    IPersonInfoService personInfoService ;
    String pathFile = FILE_PATH;

    //3- Return a list of telephone numbers of persons covered by the corresponding firestation
    @GetMapping(value = "/phoneAlert")
    public ResponseEntity<List<String>> getListPhonePersonByFireStation(@RequestParam String firestation) {
        List<String> listPhone = personInfoService.findAllListPhonePersonByFireStation(firestation, pathFile);

        if (!listPhone.isEmpty()) {
            return new ResponseEntity<>(listPhone, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(listPhone, HttpStatus.NOT_FOUND);
        }
    }
}