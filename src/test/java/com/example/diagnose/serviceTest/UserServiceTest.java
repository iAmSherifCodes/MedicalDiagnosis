package com.example.diagnose.serviceTest;

import com.example.diagnose.dto.request.DiagnoseRequest;
import com.example.diagnose.dto.request.ValidateStatusRequest;
import com.example.diagnose.dto.response.DiagnoseResponseObject;
import com.example.diagnose.dto.response.ValidateResultResponse;
import com.example.diagnose.exceptions.NotFoundException;
import com.example.diagnose.models.MedicalRecord;
import com.example.diagnose.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
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
    void diagnose() throws IOException {
        DiagnoseRequest request = new DiagnoseRequest();
        request.setGender("male");
        request.setYearOfBirth("1988");
        request.setPatientName("Doe John");
        List<Integer> symptoms = new ArrayList<>();
        symptoms.add(115);
        symptoms.add(139);
        request.setSymptoms(symptoms);
        log.info(request.toString() + ",,,,,,,>>>>>>>>>>");
        log.info("Request {}",request);

        DiagnoseResponseObject response = userService.diagnose(request);
        log.info("Request {}",response);
        assertThat(response).isNotNull();

    }

    @Test
    void testThatRegisteredUserAddDiagnoseResult() throws IOException {
        DiagnoseRequest request = new DiagnoseRequest();
        request.setGender("male");
        request.setYearOfBirth("1988");
        request.setPatientName("Doe John");
        List<Integer> symptoms = new ArrayList<>();
        symptoms.add(207);
        request.setSymptoms(symptoms);

        DiagnoseResponseObject response = userService.diagnose(request);
        log.info("Request {}",response);
        assertThat(response).isNotNull();

    }

    @Test
    void testThatNewUserCanAddDiagnoseResult() throws IOException {
        DiagnoseRequest request = new DiagnoseRequest();
        request.setGender("female");
        request.setYearOfBirth("1988");
        request.setPatientName("Doe James");
        List<Integer> symptoms = new ArrayList<>();
        symptoms.add(207);
        request.setSymptoms(symptoms);

        DiagnoseResponseObject response = userService.diagnose(request);
        log.info("Request {}",response);
        assertThat(response).isNotNull();

    }

    @Test
    void searchResults() throws IOException {
        DiagnoseRequest request = new DiagnoseRequest();
        request.setGender("female");
        request.setYearOfBirth("1988");
        request.setPatientName("Doe Palmer");
        List<Integer> symptoms = new ArrayList<>();
        symptoms.add(207);
        request.setSymptoms(symptoms);

        userService.diagnose(request);

        List<MedicalRecord> medicalRecords = userService.searchResults(request.getPatientName());

        assertThat(medicalRecords).isNotNull();
    }

    @Test
    void validateDiagnoseResult() throws IOException, NotFoundException {
        DiagnoseRequest request = new DiagnoseRequest();
        request.setGender("MALE");
        request.setYearOfBirth("1988");
        request.setPatientName("Doe Palmer");
        List<Integer> symptoms = new ArrayList<>();
        symptoms.add(207);
        request.setSymptoms(symptoms);

        DiagnoseResponseObject responseObject = userService.diagnose(request);
        log.info("Requests {}", responseObject.toString());


        ValidateStatusRequest validateStatusRequest = new ValidateStatusRequest();
        validateStatusRequest.setStatus("VALID");
        validateStatusRequest.setPatientName("Doe Palmer");
        validateStatusRequest.setMedicalRecordId(responseObject.getMedicalRecordId());
        ValidateResultResponse validateResultResponse = userService.validateDiagnoseResult(validateStatusRequest);

        log.info("Requestss {}", validateResultResponse.toString());
        assertThat(validateResultResponse).isNotNull();
    }

}
