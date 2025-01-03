package com.safetynet.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.service.DataJsonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataJsonServiceITest {
    @Autowired
    private DataJsonService dataJsonService;
    @Autowired
    DataJsonContainer dataJsonContainer;
    @Autowired
    ObjectMapper objectMapper;
    private String pathFile = "src/test/resources/dataTest.json";

    @Test
    public void readFileJsonITest() {
        try {
            File file = new File(pathFile);
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            assertNotNull(dataJsonContainer);
            assertEquals(dataJsonContainer, objectMapper.readValue(file, DataJsonContainer.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeFileJsonTest_FileExists() {
        try {
            File file = new File(pathFile);
            boolean existFile = file.exists();
            if (existFile) {
                dataJsonService.writeFileJson(dataJsonContainer, pathFile);

            } else {
                boolean createFile = file.createNewFile();
                dataJsonService.writeFileJson(dataJsonContainer, pathFile);
                assertTrue(createFile);
            }
            // Lire le fichier apres ecriture
            DataJsonContainer writtenData = objectMapper.readValue(file, DataJsonContainer.class);
            assertEquals(dataJsonContainer, writtenData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
