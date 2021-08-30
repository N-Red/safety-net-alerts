package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /* GET */

    //http://localhost:8080/Persons
    @RequestMapping(value = "/Persons", method = RequestMethod.GET)
    public List<Person> getListOfPersons() {
        return this.personService.getList();
    }

    //http://localhost:8080/communityEmail?city=<city>
    @RequestMapping(value = "communityEmail", method = RequestMethod.GET)
    public List<String> listEmails(@RequestParam(name = "city") String city) {
        return this.personService.findAllEmailsByCity(city);
    }

    //http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    @RequestMapping(value = "personInfo", method = RequestMethod.GET)
    public PersonInfoDto findPersonInfo(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        return this.personService.findPersonInfo(firstName, lastName);
    }

    //http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    @RequestMapping(value = "flood/stations", method = RequestMethod.GET)
    public List <FloodDto> findFloodStations(@RequestParam(name = "stations") List<Integer> fireStation) {
        return this.personService.findFloodStations(fireStation);
    }

    /* Get with Dto */
    //http://localhost:8080/firestation?stationNumber=<stationNumber>
    @RequestMapping(value = "firestation", method = RequestMethod.GET)
    public FireStationDto findPersonsByFireStationNumber(@RequestParam(name = "stationNumber") int stationNumber) {
        return this.personService.findPersonsByFireStation(stationNumber);
    }

    //http://localhost:8080/childAlert?address=<address>
    @RequestMapping(value = "childAlert", method = RequestMethod.GET)
    public List<ChildAlertDto> listOfChildren(@RequestParam(name = "address") String address) {
        return this.personService.findChildrenByAddress(address);
    }

    //http://localhost:8080/fire?address=<address>
    @RequestMapping(value = "fire", method = RequestMethod.GET)
    public List<FireDto> findPersonsByAddressWithFireStationNumber(@RequestParam(name = "address") String address) {
        return this.personService.findPersonsWithMedicalRecordAndFireStationByAddress(address);
    }



    /*POST*/

    //http://localhost:8080/person=<person>
    @PostMapping(value = "person")
    public void addAPerson(@RequestBody Person person) {
        personService.addPerson(person);
    }

    /*DELETE*/

    //http://localhost:8080/deletePerson=<person>
    @DeleteMapping(value = "deletePerson")
    public void deleteAPerson(@RequestBody Person person) {
        personService.deleteAPerson(person);
    }

    /* PUT */
    @PutMapping(value="putPerson")
    public void putAPerson(@RequestBody Person person) {
        personService.putAPerson(person);
    }
}