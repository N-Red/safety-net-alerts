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

    /* GET */

    @RequestMapping(value = "/Firestations", method = RequestMethod.GET)
    public List<FireStation> getListOfFirestation() {
        return this.fireStationService.getList();
    }

    //http://localhost:8080/phoneAlert?firestation=<firestation_number>
    @RequestMapping(value = "phoneAlert", method = RequestMethod.GET)
    public List<String> findPhoneByFirestation(@RequestParam(name = "firestation") Integer firestation_number) {
        return this.fireStationService.findPhoneNumbersByStationNumber(firestation_number);
    }

    /* POST */

    //http://localhost:8080/firestation=<firestation>
    @PostMapping(value = "firestation")
    public void addAFireStation(@RequestBody FireStation fireStation) {
        fireStationService.addFireStation(fireStation);
    }

    /* DELETE */

    //http://localhost:8080/deleteFireStation=<firestation>
    @DeleteMapping(value = "deleteFireStation")
    public void deleteAFireStation(@RequestBody FireStation fireStation) {
        fireStationService.deleteAPerson(fireStation);
    }

    /* PUT */

    //http://localhost:8080/putFireStation=<firestation>
    @PutMapping(value = "putFireStation")
    public void putAFireStation(@RequestBody FireStation fireStation){
        fireStationService.putAFireStation(fireStation);
    }
}
