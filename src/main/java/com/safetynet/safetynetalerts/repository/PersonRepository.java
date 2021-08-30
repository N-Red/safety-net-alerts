package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
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

    /* GET */

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
                .filter(p -> !p.getFirstName().equals(person.getFirstName()))
                .filter(p -> p.getAddress().equals(person.getAddress()))
                .collect(Collectors.toList());
    }

    /* POST*/

    public void addPerson(Person person) {
        dataHandler.getData().getPersons().add(person);
        dataHandler.save();
    }

    /* DELETE */

    public void deletePerson(Person person) {
        if (lookForIndexOfPerson(person) != -1) {
            dataHandler.getData().getPersons().remove(lookForIndexOfPerson(person));
        }
    }

    private int lookForIndexOfPerson(Person person) {
        List<Person> personList = findAllPersons();
        int index = -1;
        for (int i = 0; i < personList.size(); i++) {
            Person p = personList.get(i);
            if (p.getLastName().equals(person.getLastName())
                    && p.getFirstName().equals(person.getFirstName())) {
                index = i;
            }
        }
        return index;
    }

    /* PUT */

    public void putAPerson(Person person) {
        // look for the person to update
        List<Person> persons = dataHandler.getData().getPersons();
        for (Person p : persons) {
            if (p.getFirstName().equals((person.getFirstName()))
                    && p.getLastName().equals(person.getLastName())) {
                p.setAddress(person.getAddress());
                p.setCity(person.getCity());
                p.setEmail(person.getEmail());
                p.setZip(person.getZip());
                p.setPhone(person.getPhone());
            }
        }
        // set the new properties of fireStation
        dataHandler.getData().setPersons(persons);
        dataHandler.save();
    }
}