package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.repository.FireStationRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {
    private FireStationRepository fireStationRepository;
    private PersonRepository personRepository;

    public FireStationService(FireStationRepository fireStationRepository, PersonRepository personRepository) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
    }

    /* GET */

    public List<FireStation> getList() {
        return this.fireStationRepository.findAllFireStations();
    }

    public List<String> findPhoneNumbersByStationNumber(int number) {
        List<String> result = new ArrayList<>();

        List<FireStation> fireStations = fireStationRepository.findAllFireStationsAddressByNumber(number);
        List<Person> persons = personRepository.findAllPersons();
        for (Person person : persons) {
            if (personsContainsFireStationAddress(fireStations, person)) {
                result.add(person.getPhone());
            }
        }
        return result;
    }

    private boolean personsContainsFireStationAddress(List<FireStation> fireStations, Person person) {
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(person.getAddress())) {
                return true;
            }
        }
        return false;
    }

    /* POST */

    public void addFireStation(FireStation fireStation) {
        fireStationRepository.saveFireStation(fireStation);
    }

    /* DELETE */

    public void deleteAPerson(FireStation fireStation) {
        if(containsInFireStationList(fireStation)){
            fireStationRepository.deleteFireStation(fireStation);
        }

    }

    private boolean containsInFireStationList(FireStation fireStation){
        List <FireStation> fireStations = fireStationRepository.findAllFireStations();
        for (FireStation fs : fireStations){
            if(fs.getAddress().equals(fireStation.getAddress())){
                return true;
            }
        }
        return false;
    }

    /* PUT */

    public void putAFireStation(FireStation fireStation) {
            fireStationRepository.putAFireStation(fireStation);
    }


}
