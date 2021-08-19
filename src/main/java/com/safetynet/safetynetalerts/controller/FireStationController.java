package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FireStationController {
    @Autowired
    private FireStationService fireStationService;

    @RequestMapping(value = "/Firestations", method = RequestMethod.GET)
    public List<FireStation> getListOfFirestation() {
        return this.fireStationService.getList();
    }

    //http://localhost:8080/phoneAlert?firestation=<firestation_number>
    @RequestMapping(value = "phoneAlert", method = RequestMethod.GET)
    public List<String> findPhoneByFirestation(@RequestParam(name = "firestation") Integer firestation_number) {
        return this.fireStationService.findPhoneNumbersByStationNumber(firestation_number);
    }

    /*POST*/
    //http://localhost:8080/person=<Person person>
    @PostMapping(value = "firestation")
    public void addAFireStation(@RequestBody FireStation fireStation) {
        fireStationService.addFireStation(fireStation);
    }
}
