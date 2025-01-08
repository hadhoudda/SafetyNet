package com.safetynet.api.controller;

import com.safetynet.api.dto.PersonInfosDto;
import com.safetynet.api.service.contracts.IPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.safetynet.api.constants.Path.FILE_PATH;

@RestController
public class PersonInfosByLastNameController {

    @Autowired
    IPersonInfoService personInfoService;
    String pathFile = FILE_PATH;

    //6- Return information about a person
    @GetMapping(value = "/personInfolastName={lastName}")
    public ResponseEntity<List<PersonInfosDto>> getPersonInfoByLastName(@PathVariable String lastName){
        List<PersonInfosDto> personInfosDtoList = personInfoService.findAllPersonInfos(lastName, pathFile);

        if (!personInfosDtoList.isEmpty()){
            return new ResponseEntity<>(personInfosDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(personInfosDtoList,HttpStatus.NOT_FOUND);
        }
    }
}