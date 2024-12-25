package com.safetynet.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.service.contracts.IDataJsonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class DataJsonService implements IDataJsonService {

    private  static  final Logger logger = LogManager.getLogger(DataJsonService.class);
    @Override
    public DataJsonContainer readFileJson(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource(path);
        DataJsonContainer data ;
        try (InputStream inputStream = resource.getInputStream()) {
           data  = objectMapper.readValue(inputStream, DataJsonContainer.class);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void writeFileJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");
        try {
            if (file.exists()) {
                objectMapper.writeValue(file, object);
                logger.info("Objet écrit dans le fichier existant");
            } else {
                if (file.createNewFile()) {
                    objectMapper.writeValue(file, object);
                    logger.info("Fichier créé et objet écrit");
                } else {
                    logger.error("Erreur : Impossible de créer le fichier");
                }
            }
        } catch (IOException e) {
            logger.error("Erreur lors de l'écriture dans le fichier JSON : {}", e.getMessage());
            e.printStackTrace();
        }
    }
}