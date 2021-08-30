package com.safetynet.safetynetalerts;


import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.FireStationService;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.service.dto.ChildAlertDto;
import com.safetynet.safetynetalerts.service.dto.FireStationDto;
import com.safetynet.safetynetalerts.service.dto.PersonInfoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class SafetyNetAlertsApplicationTests {

    @Autowired
    private PersonService personService;


    @Test
    public void computeAgeTest() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setBirthdate("01/01/2000");
        int age = personService.computeAge(medicalRecord.getBirthdate());

        Assertions.assertEquals(21, age);
    }

    @Test
    public void findPersonsByFireStationTest() {
        int fireStationNumber = 1;
        FireStationDto fireStationDto = personService.findPersonsByFireStation(fireStationNumber);

        Assertions.assertNotNull(fireStationDto);
    }

    @Test
    public void findAllEmailsByCityTest() {
        String city = "Culver";
        List<String> stringList = personService.findAllEmailsByCity(city);
        Assertions.assertNotNull(stringList);
    }

    @Test
    public void findChildrenByAddressTest() {
        String address = "1509 Culver St";

        List<ChildAlertDto> childAlertDtoList = personService.findChildrenByAddress(address);

        Assertions.assertNotNull(childAlertDtoList);
    }

    @Test
    public void findPersonInfoTest() {
        String firstName = "Jacob";
        String lastName = "Boyd";
        PersonInfoDto personInfoDto = personService.findPersonInfo(firstName, lastName);

        Assertions.assertEquals(lastName, personInfoDto.getLastName());
        Assertions.assertNotNull(personInfoDto);
    }

    @Test
    public void addPersonTest() {
        Person person = new Person();
        person.setFirstName("Test");
        person.setLastName("Test");

        boolean isInsideArray = false;
        personService.addPerson(person);

        List<Person> persons = personService.getList();

        for (Person p : persons) {
            if (p.getFirstName().equals(person.getFirstName())
                    && p.getLastName().equals(person.getLastName())) {
                isInsideArray = true;
            }
        }
        Assertions.assertEquals(true, isInsideArray);
    }

    @Test
    public void deletePersonTest() {
        Person person = new Person();
        person.setFirstName("Test");
        person.setLastName("Test");
        personService.addPerson(person);


        personService.deleteAPerson(person);

        boolean isDeleted = true;
        for (Person p: personService.getList()) {
            if(p.getLastName().equals(person.getLastName()) && p.getFirstName().equals(person.getFirstName())){
                isDeleted = false;
            }else{
                isDeleted = true;
            }
        }
        Assertions.assertEquals(true, isDeleted);
    }


}
