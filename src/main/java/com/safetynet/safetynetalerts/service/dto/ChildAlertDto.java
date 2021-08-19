package com.safetynet.safetynetalerts.service.dto;

import com.safetynet.safetynetalerts.model.Person;

import java.util.List;

//http://localhost:8080/childAlert?address=<address>
/*
Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide.
 */
public class ChildAlertDto {
    private String firstName;
    private String lastName;
    private String age;
    private List<Person> memberOfHousehold;

    public ChildAlertDto() {
    }

    public ChildAlertDto(String firstName, String lastName, String age, List<Person> memberOfHousehold) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.memberOfHousehold = memberOfHousehold;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<Person> getMemberOfHousehold() {
        return memberOfHousehold;
    }

    public void setMemberOfHousehold(List<Person> memberOfHousehold) {
        this.memberOfHousehold = memberOfHousehold;
    }
}