package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.dto.FireStationDto;
import com.safetynet.safetynetalerts.service.dto.FireStationPersonDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private MedicalRecordRepository medicalrecordRepository;
    private FireStationRepository firestationRepository;

    public PersonService(PersonRepository personRepository, MedicalRecordRepository medicalrecordRepository, FireStationRepository firestationRepository) {
        this.personRepository = personRepository;
        this.medicalrecordRepository = medicalrecordRepository;
        this.firestationRepository = firestationRepository;
    }

    public List<Person> getList() {
        return this.personRepository.findAllPersons();
    }

    public List<String> findAllEmailsByCity(String city) {
        return this.personRepository.findAllPersons().stream()
                .filter(p -> p.getCity().equals(city))
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }

    public List<String> findAllChildrenByAddress(String address) {
        List<String> result = new ArrayList<>();
        List<MedicalRecord> medicalrecords = medicalrecordRepository.findAllMedicalRecords();
        List<Person> persons = personRepository.findAllPersonsByAddress(address);

        for (MedicalRecord medicalRecord : medicalrecords) {
            for (Person person : persons) {
                //To link Person with medicalRecord, same first name and last name
                if (personsContainsMedicalRecord(medicalRecord, person)) {
                    int age = calculateAge(medicalRecord.getBirthdate());
                    if (age < 18) {
                        result.add("First Name : " + person.getFirstName());
                        result.add("Last Name : " + person.getLastName());
                        result.add("Age : " + age + " ans");

                        List<Person> membersOfHousehold = personRepository.findAllMembersOfHousehold(person);
                        result.add("Members of household :");
                        for (Person member : membersOfHousehold) {
                            result.add("First Name : " + member.getFirstName());
                            result.add("Last Name : " + member.getLastName());
                        }
                    }
                }
            }
        }
        return result;
    }

    public List<String> findPersonsByFireStationNumber(int stationNumber) {
        List<String> result = new ArrayList<>();
        List<Person> persons = this.personRepository.findAllPersons();
        List<FireStation> fireStations = this.firestationRepository.findAllFireStationsAddressByNumber(stationNumber);
        List<MedicalRecord> medicalRecords = this.medicalrecordRepository.findAllMedicalRecords();
        int totalAdult = 0;
        int totalChildren = 0;
        for (FireStation fireStation : fireStations) {
            result.add("Station : " + fireStation.getStation());
            result.add("Address : " + fireStation.getAddress());
            for (Person person : persons) {
                if (fireStation.getAddress().equals(person.getAddress())) {
                    for (MedicalRecord medicalRecord : medicalRecords) {
                        if (personsContainsMedicalRecord(medicalRecord, person)) {
                            int age = calculateAge(medicalRecord.getBirthdate());
                            if (age > 18) {
                                totalAdult++;
                            } else {
                                totalChildren++;
                            }
                            result.add("First Name : " + person.getFirstName());
                            result.add("Last Name : " + person.getLastName());
                            result.add("Address : " + person.getAddress());
                            result.add("Phone : " + person.getPhone());
                        }
                    }
                }
            }
        }
        result.add("Total Adults : " + totalAdult);
        result.add("Total Children : " + totalChildren);
        return result;
    }

    public List<String> findPersonsByAddressWithFireStationNumberAndMedicalRecord(String address) {
        List<String> result = new ArrayList<>();
        List<Person> persons = personRepository.findAllPersonsByAddress(address);
        List<FireStation> firestations = firestationRepository.findAllFireStationsNumberByAddress(address);
        List<MedicalRecord> medicalrecords = medicalrecordRepository.findAllMedicalRecords();

        for (FireStation fireStation : firestations) {
            result.add("Station Number : " + fireStation.getStation());
        }
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalrecords) {
                if (personsContainsMedicalRecord(medicalRecord, person)) {
                    int age = calculateAge(medicalRecord.getBirthdate());
                    result.add("Last Name : " + person.getLastName());
                    result.add("Phone : " + person.getPhone());
                    result.add("Age : " + age + "ans");
                    result.add("Allergies : " + medicalRecord.getAllergies());
                    result.add("Medications : " + medicalRecord.getMedications());
                }
            }
        }
        return result;
    }

    public List<String> findPersonInfo(String firstName, String lastName) {

        List<String> result = new ArrayList<>();
        List<Person> persons = personRepository.findAllPersonsByName(firstName, lastName);
        List<MedicalRecord> medicalrecords = medicalrecordRepository.findAllMedicalRecords();

        for (Person person : persons) {
            int age = 0;
            String medications = null;
            String allergies = null;

            for (MedicalRecord medicalRecord : medicalrecords) {
                if (personsContainsMedicalRecord(medicalRecord, person)) {
                    age = calculateAge(medicalRecord.getBirthdate());
                    medications = String.valueOf(medicalRecord.getMedications());
                    allergies = String.valueOf(medicalRecord.getAllergies());
                }
            }

            result.add("Last Name : " + person.getLastName());
            result.add("Address : " + person.getAddress());
            result.add("Age : " + age + "ans");
            result.add("Email : " + person.getEmail());
            result.add("Medications : " + medications);
            result.add("Allergies : " + allergies);
        }
        return result;
    }

    public List<String> findFloodStations(List<Integer> integers) {
        List<String> result = new ArrayList<>();
        List<MedicalRecord> medicalrecords = medicalrecordRepository.findAllMedicalRecords();

        for (Integer integer : integers) {

            List<FireStation> firestations = firestationRepository.findAllFireStationsAddressByNumber(integer);
            result.add("Station :" + integer);
            for (FireStation firestation : firestations) {

                List<Person> persons = personRepository.findAllPersonsByAddress(firestation.getAddress());
                for (Person person : persons) {

                    result.add(person.getFirstName() + " " + person.getLastName());
                    List<Person> membersOfHousehold = personRepository.findAllMembersOfHousehold(person);

                    for (MedicalRecord medicalRecord : medicalrecords) {
                        if (personsContainsMedicalRecord(medicalRecord, person)) {
                            int age = calculateAge(medicalRecord.getBirthdate());
                            result.add(age + "ans");
                            result.add(String.valueOf(medicalRecord.getAllergies()));
                            result.add(String.valueOf(medicalRecord.getMedications()));
                        }
                    }
                    result.add("Members of Household :");
                    for (Person member : membersOfHousehold) {
                        result.add(member.getFirstName() + " " + member.getLastName());
                        for (MedicalRecord medicalRecord : medicalrecords) {
                            if (personsContainsMedicalRecord(medicalRecord, member)) {
                                int age = calculateAge(medicalRecord.getBirthdate());
                                result.add(age + "ans");
                                result.add(String.valueOf(medicalRecord.getAllergies()));
                                result.add(String.valueOf(medicalRecord.getMedications()));
                            }
                        }
                    }

                }
            }
        }
        return result;
    }

    private int calculateAge(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        LocalDate currentDate = LocalDate.now();
        LocalDate birthdate = LocalDate.parse(string, formatter);
        Period age = Period.between(birthdate, currentDate);
        return age.getYears();
    }

    private boolean personsContainsMedicalRecord(MedicalRecord medicalRecord, Person person) {
        if (medicalRecord.getLastName().equals(person.getLastName()) &&
                medicalRecord.getFirstName().equals(person.getFirstName())) {
            return true;
        }
        return false;
    }

    /* DTO */
    public FireStationDto findPersonsByFireStation(int stationNumber) {

        FireStationDto result = new FireStationDto();
        List<FireStationPersonDto> people = new ArrayList<>();
        result.setPeople(people);

        List<FireStation> fireStations = firestationRepository.findAllFireStationsAddressByNumber(stationNumber);
        List<MedicalRecord> medicalRecords = medicalrecordRepository.findAllMedicalRecords();
        List<Person> persons = personRepository.findAllPersons();

        int childrenCount = 0;
        int adultsCount = 0;

        for (Person person : persons) {
            FireStation fireStation = fireStationContainPersons(fireStations, person);
            if (fireStation != null) {
                FireStationPersonDto fireStationPersonDto = new FireStationPersonDto();
                fireStationPersonDto.setFirstName(person.getFirstName());
                fireStationPersonDto.setLastName(person.getLastName());
                fireStationPersonDto.setAddress(person.getAddress());
                fireStationPersonDto.setPhoneNumber(person.getPhone());

                for (Person person2 : persons) {
                    if (person == person2) {
                        MedicalRecord medicalRecord = medicalRecordsContainsPerson(medicalRecords, person2);
                        if (medicalRecord != null) {
                            if ((computeAge(medicalRecord.getBirthdate()) < 18)) {
                                childrenCount++;
                            } else
                                adultsCount++;
                        }
                    }
                }
                result.setTotalChildren(childrenCount);
                result.setTotalAdults(adultsCount);
                result.getPeople().add(fireStationPersonDto);
            }

        }
        return result;
    }

    private int computeAge(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        LocalDate currentDate = LocalDate.now();
        LocalDate birthdate = LocalDate.parse(string, formatter);
        Period age = Period.between(birthdate, currentDate);
        return age.getYears();
    }

    private MedicalRecord medicalRecordsContainsPerson(List<MedicalRecord> medicalRecords, Person person) {
        MedicalRecord result = new MedicalRecord();
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getLastName().equals(person.getLastName()) &&
                    medicalRecord.getFirstName().equals(person.getFirstName())) {

                result.setFirstName(medicalRecord.getFirstName());
                result.setLastName(medicalRecord.getLastName());
                result.setMedications(medicalRecord.getMedications());
                result.setAllergies(medicalRecord.getAllergies());
                result.setBirthdate(medicalRecord.getBirthdate());

            }
        }
        return result;
    }

    private FireStation fireStationContainPersons(List<FireStation> fireStations, Person person) {
        FireStation result = new FireStation();
        for (FireStation fireStation : fireStations) {
            if (
                    fireStation.getAddress().equals(person.getAddress())
            ) {
                result.setStation(fireStation.getStation());
                result.setAddress(fireStation.getAddress());
                return result;
            }
        }
        return null;
    }


    /* POST*/

    public void addPerson(Person person) {
        if (isValid(person)){
            personRepository.savePerson(person);
        }else{
            System.out.println("Error : no valid add Person");
        }
    }
    private boolean isValid(Person person) {
        if (person != null) {
            //CHECK IS PERSON HAVE THE SAME PROPRIETIES
            return true;
        }
        return false;
    }


}