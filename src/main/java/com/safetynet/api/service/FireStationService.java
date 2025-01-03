package com.safetynet.api.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.contracts.IDataJsonService;
import com.safetynet.api.service.contracts.IFireStationService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.safetynet.api.constants.Path.FILE_PATH;


@Service
public class FireStationService implements IFireStationService {

    private static final Logger logger = LogManager.getLogger(FireStationService.class);
    @Autowired
    IDataJsonService dataJsonService;
    DataJsonContainer dataJsonContainer;
    private String pathFile = FILE_PATH;
    
    public boolean existsFireStation(FireStation fireStation, String pathFile){
        try {
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            boolean exist = dataJsonContainer.getFireStationList().contains(fireStation);
            if (exist){
                return true;
            }
            else return false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean addFireStation(FireStation fireStation, String pathFile) {
        try {
            if (existsFireStation(fireStation, pathFile)) {
                logger.error("Error : firestation exists");
                return false;
            } else {
                dataJsonContainer.getFireStationList().add(fireStation);
                dataJsonService.writeFileJson(dataJsonContainer, pathFile);

                return true;
            }
        } catch (Exception e) {
            logger.error("Error writing to JSON file", e);
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }


    @Override
    public boolean updateFireStation(FireStation fireStation, String pathFile ) {
        try {

            boolean fireStationExist = false;
            for (int i = 0; i < dataJsonContainer.getFireStationList().size(); i++) {
                FireStation existingFireStation = dataJsonContainer.getFireStationList().get(i);
                if (existingFireStation.getAddress().equals(fireStation.getAddress())) {
                    dataJsonContainer.getFireStationList().set(i, fireStation); //mettre à jour fireStation
                    fireStationExist = true;
                    break;
                }
            }
            if (fireStationExist) {
                dataJsonService.writeFileJson(dataJsonContainer, pathFile);
                logger.info("Successful updated firestation");
                return true;
            } else {
                logger.error("firestation is not exists");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error writing to JSON file", e);
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }

    @Override
    public boolean deleteFireStation(FireStation fireStation, String pathFile) {
        try {

            if (existsFireStation(fireStation, pathFile)) {
                dataJsonContainer.getFireStationList().removeIf(fireStation1 -> fireStation.getAddress().equals(fireStation1.getAddress()));
                dataJsonService.writeFileJson(dataJsonContainer, pathFile);
                logger.info("Successful deleted fireStation");
                return true;
            } else {
                logger.error("Error : fireStation is not exists");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error writing to JSON file", e);
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }
}
