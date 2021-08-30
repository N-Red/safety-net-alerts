package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.dto.*;
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

    /* GET */

    public List<Person> getList() {
        return this.personRepository.findAllPersons();
    }

    public List<String> findAllEmailsByCity(String city) {
        return this.personRepository.findAllPersons().stream()
                .filter(p -> p.getCity().equals(city))
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }

    public List<FloodDto> findFloodStations(List<Integer> integers) {
        List<FloodDto> result = new ArrayList<>();


        List<MedicalRecord> medicalRecords = medicalrecordRepository.findAllMedicalRecords();
        for (Integer integer : integers) {
            List<FireStation> fireStations = firestationRepository.findAllFireStationsAddressByNumber(integer);

            for (FireStation firestation : fireStations) {
                List<Person> persons = personRepository.findAllPersonsByAddress(firestation.getAddress());

                for (Person p : persons) {
                    for (MedicalRecord medicalRecord : medicalRecords) {
                        if (personsContainsMedicalRecord(medicalRecord, p)) {
                            FloodDto floodDto = new FloodDto();

                            floodDto.setPhoneNumber(p.getPhone());
                            floodDto.setAge(computeAge(medicalRecord.getBirthdate()));
                            floodDto.setAllergies(medicalRecord.getAllergies());
                            floodDto.setMedications(medicalRecord.getMedications());
                            floodDto.setLastName(p.getLastName());

                            result.add(floodDto);
                        }
                    }
                }
            }
        }
        return result;
    }

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

    public List<ChildAlertDto> findChildrenByAddress(String address) {
        List<ChildAlertDto> result = new ArrayList<>();
        List<MedicalRecord> medicalRecords = medicalrecordRepository.findAllMedicalRecords();
        List<Person> persons = personRepository.findAllPersonsByAddress(address);
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (personsContainsMedicalRecord(medicalRecord, person)) {
                    int age = computeAge(medicalRecord.getBirthdate());
                    if (age < 18) {
                        ChildAlertDto dto = new ChildAlertDto();
                        dto.setFirstName(person.getFirstName());
                        dto.setLastName(person.getLastName());
                        dto.setAge(String.valueOf(age));
                        List<Person> membersOfHousehold = personRepository.findAllMembersOfHousehold(person);
                        dto.setMemberOfHousehold(membersOfHousehold);
                        result.add(dto);
                    }
                }
            }
        }
        return result;
    }

    public List<FireDto> findPersonsWithMedicalRecordAndFireStationByAddress(String address) {
        List<FireDto> result = new ArrayList();

        List<Person> persons = personRepository.findAllPersonsByAddress(address);
        FireStation fireStation = firestationRepository.findFireStationsNumberByAddress(address);
        List<MedicalRecord> medicalRecords = medicalrecordRepository.findAllMedicalRecords();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (personsContainsMedicalRecord(medicalRecord, person)) {
                    int age = computeAge(medicalRecord.getBirthdate());
                    FireDto dto = new FireDto();
                    dto.setFireStation(fireStation.getStation());
                    dto.setLastName(person.getLastName());
                    dto.setAge(String.valueOf(age));
                    dto.setPhoneNumber(person.getPhone());
                    dto.setAllergies(medicalRecord.getAllergies());
                    dto.setMedications(medicalRecord.getMedications());
                    result.add(dto);
                }
            }
        }
        return result;
    }

    public int computeAge(String string) {
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

    private boolean personsContainsMedicalRecord(MedicalRecord medicalRecord, Person person) {
        if (medicalRecord.getLastName().equals(person.getLastName()) &&
                medicalRecord.getFirstName().equals(person.getFirstName())) {
            return true;
        }
        return false;
    }

    public PersonInfoDto findPersonInfo(String firstName, String lastName) {
        PersonInfoDto personInfoDto = new PersonInfoDto();
        List<Person> persons = personRepository.findAllPersonsByName(firstName, lastName);
        List<MedicalRecord> medicalRecords = medicalrecordRepository.findAllMedicalRecords();

        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (personsContainsMedicalRecord(medicalRecord, person)) {
                    personInfoDto.setAge(computeAge(medicalRecord.getBirthdate()));
                    personInfoDto.setAllergies(medicalRecord.getAllergies());
                    personInfoDto.setMedications(medicalRecord.getMedications());
                    personInfoDto.setLastName(person.getLastName());
                    personInfoDto.setAddress(person.getAddress());
                    personInfoDto.setEmail(person.getEmail());
                }
            }
        }
        return personInfoDto;
    }

    /* POST*/

    public void addPerson(Person person) {
        personRepository.addPerson(person);
    }

    /* DELETE */

    public void deleteAPerson(Person person) {
        if (containsInPersonList(person))
            personRepository.deletePerson(person);

    }

    private boolean containsInPersonList(Person person) {
        List<Person> persons = personRepository.findAllPersons();
        for (Person p : persons) {
            if (p.getFirstName().equals(person.getFirstName())
                    && p.getLastName().equals(person.getLastName())) {
                return true;
            }
        }
        return false;
    }

    /* PUT */

    public void putAPerson(Person person) {
        personRepository.putAPerson(person);
    }
}