package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
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

    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
        if (lookForIndexOfMedicalRecord(medicalRecord) != -1) {
            dataHandler.getData().getMedicalRecords().remove(lookForIndexOfMedicalRecord(medicalRecord));
        }
    }

    private int lookForIndexOfMedicalRecord(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords  = findAllMedicalRecords();
        int index = -1;
        for (int i = 0; i < medicalRecords.size(); i++) {
            MedicalRecord mr = medicalRecords.get(i);
            if (mr.getLastName().equals(medicalRecord.getLastName())
            && mr.getFirstName().equals(medicalRecord.getFirstName())) {
                index = i;
            }else{
            }
        }
        return index;
    }

    /* PUT */

    public void putAMedicalRecord(MedicalRecord medicalRecord) {
        // look for the person to update
        List<MedicalRecord> medicalRecords = dataHandler.getData().getMedicalRecords();
        for (MedicalRecord mr : medicalRecords) {
            if (mr.getFirstName().equals((medicalRecord.getFirstName()))
            && mr.getLastName().equals(medicalRecord.getLastName())) {
                mr.setMedications(medicalRecord.getMedications());
                mr.setAllergies(medicalRecord.getAllergies());
                mr.setBirthdate(medicalRecord.getBirthdate());
            }
        }
        // set the new properties of medicalRecord
        dataHandler.getData().setMedicalrecords(medicalRecords);
        dataHandler.save();
    }
}
