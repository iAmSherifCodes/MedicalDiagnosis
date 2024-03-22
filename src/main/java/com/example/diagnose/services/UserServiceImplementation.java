package com.example.diagnose.services;

import com.example.diagnose.constants.Gender;
import com.example.diagnose.dto.request.DiagnoseRequest;
import com.example.diagnose.dto.response.DiagnoseResult;
import com.example.diagnose.models.MedicalRecord;
import com.example.diagnose.models.User;
import com.example.diagnose.repositories.MedicalRecordRepository;
import com.example.diagnose.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service @Slf4j @AllArgsConstructor
public class UserServiceImplementation implements UserService{

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public List<DiagnoseResult> diagnose(DiagnoseRequest diagnoseRequest) throws IOException {
        System.out.println(diagnoseRequest);
        try {
            Gender gender = diagnoseRequest.getGender();
            String patientName =  diagnoseRequest.getPatientName();
            List<Integer> symptoms = diagnoseRequest.getSymptoms();

            String yearOfBirth = diagnoseRequest.getYearOfBirth();
            int yob = Integer.parseInt(yearOfBirth);
            LocalDate date = LocalDate.now();
            if (yob < date.getYear() - 200 || yob > date.getYear()){
                throw new IOException("Invalid Year Of Birth");
            }

            String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImNoZXJyeXBoYXdvZmlyYW55ZUBnbWFpbC5jb20iLCJyb2xlIjoiVXNlciIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL3NpZCI6IjEzNjk4IiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy92ZXJzaW9uIjoiMjAwIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9saW1pdCI6Ijk5OTk5OTk5OSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcCI6IlByZW1pdW0iLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xhbmd1YWdlIjoiZW4tZ2IiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiIyMDk5LTEyLTMxIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9tZW1iZXJzaGlwc3RhcnQiOiIyMDI0LTAzLTIwIiwiaXNzIjoiaHR0cHM6Ly9zYW5kYm94LWF1dGhzZXJ2aWNlLnByaWFpZC5jaCIsImF1ZCI6Imh0dHBzOi8vaGVhbHRoc2VydmljZS5wcmlhaWQuY2giLCJleHAiOjE3MTExMDI3MTYsIm5iZiI6MTcxMTA5NTUxNn0.GzWCX6KnYXplo1Xd5hlSWFrrp3juVKTvpeUDWr83RL0";
            String baseUrl = "https://sandbox-healthservice.priaid.ch/";
            String url = baseUrl + "diagnosis" +
                    "?token=" +
                    token +
                    "&language=en-gb&symptoms=" +
                    symptoms +
                    "&gender=" +
                    gender.toString().toLowerCase() +
                    "&year_of_birth=" +
                    yob;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.set("Content-Type", "application/json");
            HttpEntity<DiagnoseRequest> request =
                    new HttpEntity<>(diagnoseRequest, headers);
            var response = restTemplate.getForEntity(url, Object.class);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonArray = null;
            try {
                jsonArray = objectMapper.writeValueAsString(response.getBody());
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            List<DiagnoseResult> res = objectMapper.readValue(jsonArray, new TypeReference<>() {
            });

            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setSymptoms(symptoms);
            medicalRecord.setDiagnoseResults(res);

            medicalRecordRepository.save(medicalRecord);

            List<MedicalRecord> medicalRecords = medicalRecordRepository.findBySymptoms(symptoms);

            User user = new User();
            user.setPatientName(patientName);
            user.setMedicalRecords(medicalRecords);

            userRepository.save(user);


            return res;
        } catch (IOException e){
            throw new IOException(e + "Calls can't be reached");
        }

    }
}
