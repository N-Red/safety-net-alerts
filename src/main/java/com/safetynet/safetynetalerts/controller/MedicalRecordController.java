package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    /* GET */

    @GetMapping(value = "/MedicalRecords")
    public List<MedicalRecord> medicalRecordsList() {
        return medicalRecordService.getList();
    }

    /* POST */

    //http://localhost:8080/medicalRecord=<medicalRecord>
    @PostMapping(value = "medicalRecord")
    public void addAPerson(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.addMedicalRecord(medicalRecord);
    }

    /* DELETE */

    //http://localhost:8080/deleteMedicalRecord=<medicalRecord>
    @DeleteMapping(value = "deleteMedicalRecord")
    public void deleteAMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.deleteARecordService(medicalRecord);
    }

    /* PUT */

    //http://localhost:8080/putMedicalRecord=<medicalRecord>
    @PutMapping(value = "putMedicalRecord")
    public void putAMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        medicalRecordService.putAMedicalRecord(medicalRecord);
    }
}
