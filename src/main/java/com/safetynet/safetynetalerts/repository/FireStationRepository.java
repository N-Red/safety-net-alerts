package com.safetynet.safetynetalerts.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.dto.FireDto;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository {
    private final DataHandler dataHandler;

    public FireStationRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    /* GET */

    public List<FireStation> findAllFirestations() {
        return dataHandler.getData().getFirestations();
    }

    public List<FireStation> findAllFireStationsAddressByNumber(Integer number) {
        String num = String.valueOf(number);
        return dataHandler.getData().getFirestations().stream()
                .filter(p -> p.getStation().equals(num))
                .collect(Collectors.toList());
    }

    public FireStation findFireStationsNumberByAddress(String address) {
        FireStation fireStationResult = new FireStation();
        List<FireStation> fireStations = dataHandler.getData().getFirestations().stream().collect(Collectors.toList());
        for (FireStation fireStation : fireStations){
            if(fireStation.getAddress().equals(address)){
                fireStationResult.setStation(fireStation.getStation());
                fireStationResult.setAddress(fireStation.getAddress());
            }
        }
        return fireStationResult;

    }

    /* POST */

    public void saveFireStation(FireStation fireStation) {
        dataHandler.getData().getFirestations().add(fireStation);
        dataHandler.save();
    }

    /* DELETE */

    /* PUT */

}
