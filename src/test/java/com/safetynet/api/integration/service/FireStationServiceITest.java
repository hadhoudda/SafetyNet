package com.safetynet.api.integration.service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.DataJsonService;
import com.safetynet.api.service.contracts.IFireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationServiceITest {
    @Autowired
    DataJsonService dataJsonService;
    @Autowired
    IFireStationService fireStationService;
    private final String pathFile = "src/test/resources/dataTest.json";
    FireStation fireStation = new FireStation("5 rue boileau", "7");

    @Test
    public void existsFireStationITest(){
        try {
            boolean result = fireStationService.existsFireStation(fireStation, pathFile);
            assertFalse(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add_Update_DeletedFireStationITest() {
        try {
            existsFireStationITest();
            boolean fireStationAdded = fireStationService.addFireStation(fireStation, pathFile);
            boolean fireStationUpdated = fireStationService.updateFireStation(fireStation, pathFile);
            boolean fireStationDeleted = fireStationService.deleteFireStation(fireStation, pathFile);
            assertTrue(fireStationAdded);
            assertTrue(fireStationUpdated);
            assertTrue(fireStationDeleted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}