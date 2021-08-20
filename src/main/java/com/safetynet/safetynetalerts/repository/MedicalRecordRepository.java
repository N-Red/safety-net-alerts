package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepository {
    private final DataHandler dataHandler;

    public MedicalRecordRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    /* GET */

    public List<MedicalRecord> findAllMedicalRecords() {
        return dataHandler.getData().getMedicalRecords();
    }

    /* POST */

    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        dataHandler.getData().getMedicalRecords().add(medicalRecord);
        dataHandler.save();
    }

    /* DELETE */

    /* PUT */

}
