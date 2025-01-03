package com.safetynet.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.service.contracts.IDataJsonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DataJsonService implements IDataJsonService {

    private  static  final Logger logger = LogManager.getLogger(DataJsonService.class);
    private final ObjectMapper objectMapper;

    public DataJsonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public DataJsonContainer readFileJson(String path) {
        File file = new File(path);
        DataJsonContainer data = null;
        try {
            data = objectMapper.readValue(file, DataJsonContainer.class);
            logger.info("Fichier JSON lu avec succès");
        } catch (IOException e) {
            logger.error("Erreur lors de la lecture du fichier JSON : {}", e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void writeFileJson(DataJsonContainer data, String path) {
        File file = new File(path);
        try {
            if (file.exists()) {
                objectMapper.writeValue(file, data);
                logger.info("Objet écrit dans le fichier existant");
            } else {
                if (file.createNewFile()) {
                    objectMapper.writeValue(file, data);
                    logger.info("Fichier créé et objet écrit");
                } else {
                    logger.error("Erreur : Impossible de créer le fichier");
                }
            }
        } catch (IOException e) {
            logger.error("Erreur lors de l'écriture dans le fichier JSON : {}", e.getMessage());

        }

    }
}