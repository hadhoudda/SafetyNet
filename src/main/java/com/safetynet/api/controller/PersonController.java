package com.safetynet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.DataJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PersonController {

    private static final String FILE_PATH = "data.json";
    DataJsonService dataJsonService = new DataJsonService();
    DataJsonContainer dataJsonContainer = new DataJsonContainer();

    @GetMapping("/person")
    public DataJsonContainer getPersons() throws IOException {
        dataJsonContainer = dataJsonService.readFileJson(dataJsonContainer, FILE_PATH);
        return dataJsonContainer;
    }


    @Autowired
    private ObjectMapper objectMapper;
    @GetMapping(value = "/firestation")
    public List<Person> getFilteredPersons(@RequestParam String stationNumber ) throws IOException {

        dataJsonContainer = dataJsonService.readFileJson(dataJsonContainer, FILE_PATH);
        //obtenir l'adress de stationNumber
        List<String> addresses = dataJsonContainer.getFireStationList().stream()
                .filter(station -> stationNumber.equals(station.getStation())) // filtrer selon le numéro de station donné au url
                .map(FireStation::getAddress)
                .toList();
        // Collecter toutes les adresses dans une liste
        System.out.println("//////////////////" + stationNumber);
        // Afficher toutes les adresses
        addresses.forEach(System.out::println);

        List<Person> filteredPersons = dataJsonContainer.getPersonsList().stream()
                .filter(person -> addresses.contains(person.getAddress()))  // Vérifier si l'adresse de la personne est dans la liste des adresses
                .toList();

        System.out.println(filteredPersons);

        return filteredPersons ;
    }


}