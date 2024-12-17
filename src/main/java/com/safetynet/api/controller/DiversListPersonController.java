package com.safetynet.api.controller;

import com.safetynet.api.constants.Path;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.dao.DiversServicePersonDao;
import com.safetynet.api.dao.DiversServicePersonDaoImpl;
import com.safetynet.api.dto.PersonListByFireStationWithCountDto;
import com.safetynet.api.service.DataJsonService;
import com.safetynet.api.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class DiversListPersonController {

    DataJsonService dataJsonService = new DataJsonService();
    DataJsonContainer dataJsonContainer = new DataJsonContainer();
    PersonService personService = new PersonService();
    DiversServicePersonDao diversServicePersonDao = new DiversServicePersonDaoImpl();

    @GetMapping("/person")
    public DataJsonContainer getPersons() throws IOException {
        dataJsonContainer = dataJsonService.readFileJson(Path.FILE_PATH);
        return dataJsonContainer;
    }

    //retourner une liste des personnes couvertes par la caserne de pompiers correspondante
    @GetMapping(value = "/firestation")
    public PersonListByFireStationWithCountDto getPersonListByFireStationWithCount(@RequestParam String stationNumber) throws IOException {
        return diversServicePersonDao.findAllPersonListByFireStationWithCount(stationNumber);

    }

    //retourner une liste d'enfants (tout individu âgé de 18 ans ou moins)habitant à cette adresse.
    //@GetMapping(value = "/childAlert")

    // retourner les adresses mail de tous les habitants de la ville
    @GetMapping(value = "/communityEmail")
    public List<String> getListMailPersonByCity(@RequestParam String city){
        return diversServicePersonDao.findAllListMailPersonByCity(city);
    }

    //retourner une liste des numeros de telephone des persons couvertes par la caserne de pompiers correspondante
    @GetMapping(value = "phoneAlert")
    public List<String> getListPhonePersonByFireStation(@RequestParam String firestation){
        return diversServicePersonDao.findAllListPhonePersonByFireStation(firestation);
    }

}