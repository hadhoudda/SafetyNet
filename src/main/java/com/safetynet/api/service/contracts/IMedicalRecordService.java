package com.safetynet.api.service.contracts;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;

public interface IMedicalRecordService {
    boolean addMedicalRecord(MedicalRecord medicalRecord);
    boolean updateMedicalRecord(MedicalRecord medicalRecord);
    boolean deleteMedicalRecord(MedicalRecord medicalRecord) ;
}
