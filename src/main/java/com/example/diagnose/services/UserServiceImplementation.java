package com.example.diagnose.services;

import com.example.diagnose.constants.Gender;
import com.example.diagnose.dto.request.DiagnoseRequest;
import com.example.diagnose.dto.response.DiagnoseResult;
import com.example.diagnose.dto.response.Issue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service @Slf4j
public class UserServiceImplementation implements UserService{

    @Override
    public DiagnoseResult diagnose(DiagnoseRequest diagnoseRequest) {
        System.out.println(diagnoseRequest);
        log.info("Request {}",diagnoseRequest);
        Gender gender = diagnoseRequest.getGender();
        List<Integer> symptoms = diagnoseRequest.getSymptoms();

        String yearOfBirth = diagnoseRequest.getYearOfBirth();
        Integer yob = Integer.parseInt(yearOfBirth);
        LocalDate date = LocalDate.now();
        if (yob < date.getYear() - 200 || yob > date.getYear()){
            throw new IllegalArgumentException("Invalid Year Of Birth");
        }
        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImNoZXJyeXBoYXdvZmlyYW55ZUBnbWFpbC5jb20iLCJyb2xlIjoiVXNlciIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL3NpZCI6IjEzNjk4IiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy92ZXJzaW9uIjoiMjAwIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9saW1pdCI6Ijk5OTk5OTk5OSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcCI6IlByZW1pdW0iLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xhbmd1YWdlIjoiZW4tZ2IiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiIyMDk5LTEyLTMxIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9tZW1iZXJzaGlwc3RhcnQiOiIyMDI0LTAzLTIwIiwiaXNzIjoiaHR0cHM6Ly9zYW5kYm94LWF1dGhzZXJ2aWNlLnByaWFpZC5jaCIsImF1ZCI6Imh0dHBzOi8vaGVhbHRoc2VydmljZS5wcmlhaWQuY2giLCJleHAiOjE3MTEwNjc5MjIsIm5iZiI6MTcxMTA2MDcyMn0.xfUOQRsKF34BjK4s2zySsef6adyW-LWfBJCWze0dtEo";

        HttpEntity <Void> httpEntity  = new HttpEntity<>(getHttpHeaders());

        String baseUrl = "https://sandbox-healthservice.priaid.ch/";
        StringBuilder url = new StringBuilder(baseUrl);
        url.append("diagnosis")
           .append("?token=")
           .append(token)
           .append("&language=en-gb&symptoms=")
           .append(symptoms)
           .append("&gender=")
           .append(gender.toString().toLowerCase())
           .append("&year_of_birth=")
           .append(yob);
        RestTemplate restTemplate = new RestTemplate();
//        log.info("Request {}", url);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Content-Type", "application/json");
        HttpEntity<DiagnoseRequest> request =
                new HttpEntity<>(diagnoseRequest, headers);
//        ResponseEntity<DiagnoseResult>
                var response = restTemplate.getForEntity(url.toString(), Object.class);

        log.info("Request {}", response.getStatusCode());
        log.info("Request {}", response.getBody());


        return null;
    }

    private HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
