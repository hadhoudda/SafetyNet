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

    /**
     * Method to read a json file
     *
     * @param path: file link to read
     * @return object: DataJsonContainer
     */
    @Override
    public DataJsonContainer readFileJson(String path) {
        File file = new File(path);
        DataJsonContainer data = null;
        try {
            data = objectMapper.readValue(file, DataJsonContainer.class);
            logger.info("JSON file read successfully");
            return data;
        } catch (IOException e) {
            logger.error("Error reading JSON file : {}", e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Method to write to a json file
     * @param data:model
     * @param path: file link to write
     */
    @Override
    public void writeFileJson(DataJsonContainer data, String path) {
        File file = new File(path);
        try {
            if (file.exists()) {
                objectMapper.writeValue(file, data);
                logger.info("Object written to existing file");
            } else {
                if (file.createNewFile()) {
                    objectMapper.writeValue(file, data);
                    logger.info("File created and object written");
                } else {
                    logger.error("Error: Unable to create file");
                }
            }
        } catch (IOException e) {
            logger.error("Error writing to JSON file : {}", e.getMessage());
        }
    }
}