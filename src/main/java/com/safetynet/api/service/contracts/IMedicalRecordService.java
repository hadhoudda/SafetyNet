package com.safetynet.api.service.contracts;

import com.safetynet.api.model.MedicalRecord;

public interface IMedicalRecordService {

    boolean existPersonByMedicalRecord(MedicalRecord medicalRecord, String pathFile);

    boolean existsMedicalRecord(MedicalRecord medicalRecord, String pathFile);

    boolean addMedicalRecord(MedicalRecord medicalRecord, String pathFile);

    boolean updateMedicalRecord(MedicalRecord medicalRecord, String pathFile);

    boolean deleteMedicalRecord(MedicalRecord medicalRecord, String pathFile) ;

}