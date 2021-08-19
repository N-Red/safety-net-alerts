package com.safetynet.safetynetalerts.service.dto;

import com.safetynet.safetynetalerts.model.MedicalRecord;

import java.util.List;

//http://localhost:8080/flood/stations?stations=<a list of station_numbers>
/*
Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom.
 */
public class FloodDto {
    private List<FireStationPersonDto> people;
    private String lastName;
    private String phoneNumber;
    private int age;
    private String [] medications;
    private String [] allergies;

    public FloodDto() {
    }

    public FloodDto(List<FireStationPersonDto> people, String lastName, String phoneNumber, int age, String[] medications, String[] allergies) {
        this.people = people;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    public List<FireStationPersonDto> getPeople() {
        return people;
    }

    public void setPeople(List<FireStationPersonDto> people) {
        this.people = people;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getMedications() {
        return medications;
    }

    public void setMedications(String[] medications) {
        this.medications = medications;
    }

    public String[] getAllergies() {
        return allergies;
    }

    public void setAllergies(String[] allergies) {
        this.allergies = allergies;
    }
}
