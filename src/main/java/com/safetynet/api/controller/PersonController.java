package com.safetynet.api.controller;

import com.safetynet.api.constants.Path;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.service.contracts.IServicePersonService;
import com.safetynet.api.service.ServicePersonService;
import com.safetynet.api.service.DataJsonService;
import com.safetynet.api.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class PersonController {

    DataJsonService dataJsonService = new DataJsonService();
    DataJsonContainer dataJsonContainer = new DataJsonContainer();
    PersonService personService = new PersonService();
    IServicePersonService iServicePersonService = new ServicePersonService();

    @GetMapping("/person")
    public DataJsonContainer getPersons() throws IOException {
        dataJsonContainer = dataJsonService.readFileJson(Path.FILE_PATH);
        return dataJsonContainer;
    }

}