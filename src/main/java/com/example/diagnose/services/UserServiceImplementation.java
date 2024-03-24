package com.example.diagnose.services;

import com.example.diagnose.configs.AppConfig;
import com.example.diagnose.constants.Gender;
import com.example.diagnose.constants.MedicalRecordStatus;
import com.example.diagnose.dto.request.DiagnoseRequest;
import com.example.diagnose.dto.request.ValidateStatusRequest;
import com.example.diagnose.dto.response.DiagnoseResponseObject;
import com.example.diagnose.dto.response.DiagnoseResult;
import com.example.diagnose.dto.response.ValidateResultResponse;
import com.example.diagnose.exceptions.NotFoundException;
import com.example.diagnose.models.MedicalRecord;
import com.example.diagnose.models.User;
import com.example.diagnose.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service @Slf4j @AllArgsConstructor
public class UserServiceImplementation implements UserService{

    private final UserRepository userRepository;
    private final AppConfig appConfig;

    @Override
    public DiagnoseResponseObject diagnose(DiagnoseRequest diagnoseRequest) throws RuntimeException {
        try {
            UUID uuid = UUID.randomUUID();
            String randomId = uuid.toString();

            Gender gender = diagnoseRequest.getGender();
            String patientName =  diagnoseRequest.getPatientName();
            List<Integer> symptoms = diagnoseRequest.getSymptoms();
            String yearOfBirth = diagnoseRequest.getYearOfBirth();

            Optional<User> foundUser = userRepository.findByPatientName(patientName);

            int yob = validateYearOfBirth(yearOfBirth);
            RestTemplate restTemplate = new RestTemplate();
            String url = getUrl(gender, symptoms, yob);
            log.info("Requestss {}", url);
            var response = restTemplate.getForEntity(url, Object.class);
            List<DiagnoseResult> diagnoseResults = convertToObject(response);
            MedicalRecord medicalRecord = getMedicalRecord(patientName, symptoms, diagnoseResults, randomId);
            if (foundUser.isPresent()){
                User user = foundUser.get();
                user.getMedicalRecords().add(medicalRecord);
                userRepository.save(user);
                DiagnoseResponseObject responseObject = getDiagnoseResponseObject(diagnoseResults, medicalRecord, user);
                return responseObject;
            }else{
            User user = new User();
            user.setPatientName(patientName);
            user.getMedicalRecords().add(medicalRecord);

            User savedUser = userRepository.save(user);

            DiagnoseResponseObject responseObject = getDiagnoseResponseObject(diagnoseResults, medicalRecord, savedUser);
            return responseObject;
            }

        } catch (RuntimeException e){
            throw new RuntimeException(e + " Calls can't be reached");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private static DiagnoseResponseObject getDiagnoseResponseObject(List<DiagnoseResult> diagnoseResults, MedicalRecord medicalRecord, User user) {
        DiagnoseResponseObject responseObject = new DiagnoseResponseObject();
        responseObject.setDiagnoseResults(diagnoseResults);
        responseObject.setUserId(user.getId());
        responseObject.setMedicalRecordId(medicalRecord.getId());
        return responseObject;
    }

    private MedicalRecord getMedicalRecord(String patientName, List<Integer> symptoms, List<DiagnoseResult> diagnoseResults, String randomId) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setSymptoms(symptoms);
        medicalRecord.setDiagnoseResults(diagnoseResults);
        medicalRecord.setPatientName(patientName);
        medicalRecord.setId(randomId);

        return medicalRecord;
    }

    private static List<DiagnoseResult> convertToObject(ResponseEntity<Object> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArray = null;
        try {
            jsonArray = objectMapper.writeValueAsString(response.getBody());
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<DiagnoseResult> diagnoseResults = objectMapper.readValue(jsonArray, new TypeReference<>() {
        });
        return diagnoseResults;
    }

    private static int validateYearOfBirth(String yearOfBirth) throws RuntimeException {
        int yob = Integer.parseInt(yearOfBirth);
        LocalDate date = LocalDate.now();
        if (yob < date.getYear() - 200 || yob > date.getYear()){
            throw new RuntimeException("Invalid Year Of Birth");
        }
        return yob;
    }

    private String getUrl(Gender gender, List<Integer> symptoms, int yob) {
        String token = appConfig.getToken();
        String baseUrl = appConfig.getBaseUrl();
        String url = baseUrl + "diagnosis" +
                "?token=" +
                token +
                "&language=en-gb&symptoms=" +
                symptoms +
                "&gender=" +
                gender.toString().toLowerCase() +
                "&year_of_birth=" +
                yob;
        return url;
    }

    @Override
    public ValidateResultResponse validateDiagnoseResult(ValidateStatusRequest validateStatusRequest) throws NotFoundException {
        String patientName = validateStatusRequest.getPatientName();
        String medicalRecordId = validateStatusRequest.getMedicalRecordId();
        MedicalRecordStatus medicalRecordStatus = MedicalRecordStatus.valueOf(validateStatusRequest.getStatus());

        Optional<User> foundUser = userRepository.findByPatientName(patientName);
        if (foundUser.isPresent()){
            User user = foundUser.get();
            List<MedicalRecord> medicalRecords = user.getMedicalRecords();
            MedicalRecord foundMedicalRecord = medicalRecords.stream()
                    .filter(record -> record.getId().equals(medicalRecordId))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Medical record not found with ID: " + medicalRecordId));
            foundMedicalRecord.setMedicalRecordStatus(medicalRecordStatus);
            userRepository.save(user);

            ValidateResultResponse resultResponse = new ValidateResultResponse();
            resultResponse.setMessage("STATUS IS SUCCESSFULLY UPDATED");

            return resultResponse;
        }
        return null;
    }

    @Override
    public List<MedicalRecord> searchResults(String patientName) {
        Optional<User> foundUser = userRepository.findByPatientName(patientName);
        if (foundUser.isPresent()){
            List<MedicalRecord> medicalRecords = foundUser.get().getMedicalRecords();
            return medicalRecords;
        }
        return null;
    }

}
