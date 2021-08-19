package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Person;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonRepository {
    private final DataHandler dataHandler;

    public PersonRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Person> findAllPersons() {
        return dataHandler.getData().getPersons();
    }

    public List<Person> findAllPersonsByAddress(String address) {
        return dataHandler.getData().getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    public List<Person> findAllPersonsByName(String firstName, String lastName) {
        return dataHandler.getData().getPersons().stream()
                .filter(person -> person.getFirstName().equals(firstName))
                .filter(person -> person.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    public List<Person> findAllMembersOfHousehold(Person person) {
        return dataHandler.getData().getPersons().stream()
                .filter(p -> p.getLastName().equals(person.getLastName()))
                .filter(p -> p.getAddress().equals(person.getAddress()))
                .filter(p -> !p.getFirstName().equals(person.getFirstName()))
                .collect(Collectors.toList());
    }

    public List<Person> findAllpersonByAddress(String address) {
        return dataHandler.getData().getPersons().stream()
                .filter(p -> p.getAddress().equals(address))
                .collect(Collectors.toList());
    }
    /* POST*/
    public void savePerson(Person person) {
        dataHandler.getData().getPersons().add(person);
        dataHandler.save();
    }
}