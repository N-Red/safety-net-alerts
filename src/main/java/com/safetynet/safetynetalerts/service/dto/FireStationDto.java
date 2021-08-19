package com.safetynet.safetynetalerts.service.dto;

import java.util.List;

/*FUNCTIONAL*/

//http://localhost:8080/firestation?stationNumber=<station_number>
/*
Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante.
Donc, si le numéro de station = 1, elle doit renvoyer les habitants couverts par la station numéro 1. La liste
doit inclure les informations spécifiques suivantes : prénom, nom, adresse, numéro de téléphone. De plus,
elle doit fournir un décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
moins) dans la zone desservie.
 */
public class FireStationDto {
    private List<FireStationPersonDto> people;
    private int totalAdults;
    private int totalChildren;

    public FireStationDto() {
    }

    public FireStationDto(List<FireStationPersonDto> people, int totalAdults, int totalChildren) {
        this.people = people;
        this.totalAdults = totalAdults;
        this.totalChildren = totalChildren;
    }

    public List<FireStationPersonDto> getPeople() {
        return people;
    }

    public void setPeople(List<FireStationPersonDto> people) {
        this.people = people;
    }

    public int getTotalAdults() {
        return totalAdults;
    }

    public void setTotalAdults(int totalAdults) {
        this.totalAdults = totalAdults;
    }

    public int getTotalChildren() {
        return totalChildren;
    }

    public void setTotalChildren(int totalChildren) {
        this.totalChildren = totalChildren;
    }
}
