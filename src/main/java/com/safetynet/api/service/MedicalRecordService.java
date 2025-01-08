package com.safetynet.api.service;

import com.safetynet.api.container.DataJsonContainer;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.contracts.IDataJsonService;
import com.safetynet.api.service.contracts.IMedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);
    @Autowired
    IDataJsonService dataJsonService;
    DataJsonContainer dataJsonContainer;

    /**
     * Method to check the existence of person by medicalRecord
     *
     * @param medicalRecord: object
     * @param pathFile:      file link
     * @return true: medicalRecord exists or false: medicalRecord is not exists
     */
    public boolean existPersonByMedicalRecord(MedicalRecord medicalRecord, String pathFile) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            List<String> listLastName = dataJsonContainer.getPersonsList().stream()
                    .map(Person::getLastName).toList();
            List<String> listFirstName = dataJsonContainer.getPersonsList().stream()
                    .map(Person::getFirstName).toList();
            return listFirstName.contains(medicalRecord.getFirstName()) && listLastName.contains(medicalRecord.getLastName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to check the existence of medicalRecord
     * @param medicalRecord: object
     * @param pathFile: file link
     * @return true: medicalRecord exists or false: medicalRecord is not exists
     */
    public boolean existsMedicalRecord(MedicalRecord medicalRecord, String pathFile) {
        try {
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            List<String> listLastName = dataJsonContainer.getMedicalRecordList().stream()
                    .map(MedicalRecord::getLastName).toList();
            List<String> listFirstName = dataJsonContainer.getMedicalRecordList().stream()
                    .map(MedicalRecord::getFirstName).toList();
            return listLastName.contains(medicalRecord.getLastName()) && listFirstName.contains(medicalRecord.getFirstName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Methode to add medicalRecord
     * @param medicalRecord: object
     * @param pathFile: file link
     * @return true: medicalRecord adding or false: didn't add 
     */
    @Override
    public boolean addMedicalRecord(MedicalRecord medicalRecord, String pathFile) {
        try {
            if (existPersonByMedicalRecord(medicalRecord, pathFile)) { //verify person exists
                if (existsMedicalRecord(medicalRecord, pathFile)) { //verify medicalRecord exists
                    logger.error(" medicalRecord exists ");
                    return false;
                } else {
                    dataJsonContainer.getMedicalRecordList().add(medicalRecord);
                    dataJsonService.writeFileJson(dataJsonContainer, pathFile);
                    logger.info("medicalRecord added successfully");
                    return true;
                }
            } else {
                logger.error("person is not exists: impossible add medicalRecord ");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error writing to JSON file", e);
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }

    /**
     * Methode to update medicalRecord
     * @param medicalRecord: object
     * @param pathFile: file link
     * @return true: medicalRecord updating or false: didn't update
     */
    @Override
    public boolean updateMedicalRecord(MedicalRecord medicalRecord, String pathFile) {
        try {
            boolean existsMedicalRecod = false;
            dataJsonContainer = dataJsonService.readFileJson(pathFile);
            for (int i = 0; i < dataJsonContainer.getMedicalRecordList().size(); i++) {
                MedicalRecord medicalRecordExisting = dataJsonContainer.getMedicalRecordList().get(i);
                if (medicalRecordExisting.getLastName().equals(medicalRecord.getLastName())
                        && medicalRecordExisting.getFirstName().equals(medicalRecord.getFirstName())) {
                    dataJsonContainer.getMedicalRecordList().set(i, medicalRecord);//update medicalRecord
                    existsMedicalRecod = true;
                    break;
                }
            }
            if (existsMedicalRecod) {
                dataJsonService.writeFileJson(dataJsonContainer, pathFile);
                logger.info("Successful updated MedicalRecord ");
                return true;
            } else {
                logger.info("MedicalRecord is not exists");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error writing to JSON file", e);
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }

    /**
     * Methode to delete medicalRecord
     * @param medicalRecord: object
     * @param pathFile: file link
     * @return true: medicalRecord deleting or false: didn't delete
     */
    @Override
    public boolean deleteMedicalRecord(MedicalRecord medicalRecord, String pathFile) {
        try {
            if (existsMedicalRecord(medicalRecord, pathFile)) { //verify medicalRecord exists
                dataJsonContainer.getMedicalRecordList().remove(medicalRecord);
                dataJsonService.writeFileJson(dataJsonContainer, pathFile);
                logger.info("Successful deleted medicalRecord ");
                return true;
            } else {
                logger.error("Error : medicalRecord is not exists");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error writing to JSON file", e);
            throw new RuntimeException("Error writing to JSON file", e);
        }
    }
}