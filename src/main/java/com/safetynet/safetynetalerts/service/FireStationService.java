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
        return this.fireStationRepository.findAllFirestations();
    }

    public List<String> findPhoneNumbersByStationNumber(int number) {
        List<String> result = new ArrayList<>();
        List<FireStation> fireStations = fireStationRepository.findAllFireStationsAddressByNumber(number);
        List<Person> persons = personRepository.findAllPersons();
        for (Person person : persons) {
            if (personsContainsFirestationAddress(fireStations, person)) {
                result.add(person.getPhone());
            }
        }
        return result;
    }

    private boolean personsContainsFirestationAddress(List<FireStation> fireStations, Person person) {
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(person.getAddress())) {
                return true;
            }
        }
        return false;
    }

    /* POST */

    public void addFireStation(FireStation fireStation) {
        if(isValid(fireStation)){
            fireStationRepository.saveFireStation(fireStation);
        }else{
            System.out.println("Error : no valid add FireStation");
        }
    }

    private boolean isValid(FireStation fireStation) {
        if (fireStation != null) {
            //CHECK IS PERSON HAVE THE SAME PROPRIETIES
            return true;
        }
        return false;
    }

    /* DELETE */

    /* PUT */

}
