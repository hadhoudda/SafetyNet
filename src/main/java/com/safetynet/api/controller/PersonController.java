package com.safetynet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.container.DataJsonContainer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class PersonController {

    // Nom du fichier JSON dans le répertoire resources
    private static final String FILE_PATH = "data.json";
    @GetMapping("/person")
    public DataJsonContainer getPersons() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Accès à la ressource dans le classpath (src/main/resources)
        ClassPathResource resource = new ClassPathResource(FILE_PATH);

        // Lire le fichier et mapper le JSON dans un objet PersonsContainer
        try (InputStream inputStream = resource.getInputStream()) {
            // Conversion du JSON en objet PersonsContainer
            DataJsonContainer dataJsonContainer = objectMapper.readValue(inputStream, DataJsonContainer.class);
            return dataJsonContainer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
