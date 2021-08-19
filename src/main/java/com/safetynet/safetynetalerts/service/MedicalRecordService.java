package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<MedicalRecord> getList() {
        return this.medicalRecordRepository.findAllMedicalRecords();
    }


    /* POST*/

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        if (isValid(medicalRecord)){
            medicalRecordRepository.saveMedicalRecord(medicalRecord);
        }else{
            System.out.println("Error : no valid add MedicalRecord");
        }
    }
    private boolean isValid(MedicalRecord medicalRecord) {
        if (medicalRecord != null) {
            //CHECK IS PERSON HAVE THE SAME PROPRIETIES
            return true;
        }
        return false;
    }
}
