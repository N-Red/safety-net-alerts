package com.safetynet.safetynetalerts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class SafetynetalertsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        LocalDate birthdate = LocalDate.parse("01/01/2000",formatter);
        LocalDate currentDate = LocalDate.now() ;
        Period age = Period.between(birthdate, currentDate);
        if(age.getYears()<18){
            System.out.println("Il est mineur");
        }else{
            System.out.println("Il est majeur");
        }
		System.out.println(age);
    }
}
