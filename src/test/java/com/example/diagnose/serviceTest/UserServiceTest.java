package com.example.diagnose.serviceTest;

import com.example.diagnose.constants.Gender;
import com.example.diagnose.dto.request.DiagnoseRequest;
import com.example.diagnose.dto.response.DiagnoseResult;
import com.example.diagnose.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @Slf4j
public class UserServiceTest {
    private final UserService userService;
    @Autowired
    public UserServiceTest(UserService userService){
        this.userService = userService;
    }

    @Test
    void diagnose(){
        DiagnoseRequest request = new DiagnoseRequest();
        request.setGender(Gender.MALE);
        request.setYearOfBirth("1988");
        List<Integer> symptoms = new ArrayList<>();
        symptoms.add(115);
        symptoms.add(139);
        request.setSymptoms(symptoms);
//        log.info(request.toString() + ",,,,,,,>>>>>>>>>>");
//        log.info("Request {}",request);

        DiagnoseResult response = userService.diagnose(request);
        log.info("Request {}",response);
//        System.out.println(response);
//        assertThat(response).isNull();
    }
}
