package com.safetynet.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.container.DataJsonContainer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class DataJsonService {

    public DataJsonContainer readFileJson( String path) throws IOException {
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
}
