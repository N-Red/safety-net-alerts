package com.safetynet.safetynetalerts;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/*
@SpringBootTest
public class FireStationServiceTest {
    @Autowired
    private FireStationService fireStationService;
    @Test
    public void addFireStationTest(){
        String str = "address test";
        FireStation fireStation = new FireStation();
        fireStation.setAddress(str);
        boolean isAdd = false;

        fireStationService.addFireStation(fireStation);

        List<FireStation> list = fireStationService.getList();

        for (FireStation fs: list
        ) {
            if(fs.getAddress() == fireStation.getAddress()){
                isAdd = true;
            }else{
                isAdd = false;
            }
        }

        Assertions.assertEquals(true, isAdd);
    }
}

 */