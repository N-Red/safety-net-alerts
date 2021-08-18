package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository {
    private final DataHandler dataHandler;

    public FireStationRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<FireStation> findAllFirestations() {
        return dataHandler.getData().getFirestations();
    }

    public List<FireStation> findAllFireStationsAddressByNumber(Integer number) {
        return dataHandler.getData().getFirestations().stream()
                .filter(p -> p.getStation().equals(number.toString()))
                .collect(Collectors.toList());
    }

    public List<FireStation> findAllFireStationsNumberByAddress(String address) {
        return dataHandler.getData().getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .collect(Collectors.toList());
    }
}
