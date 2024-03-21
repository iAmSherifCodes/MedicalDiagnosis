package com.example.diagnose.models;

import com.example.diagnose.constants.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter @Setter @Document
public class User {
    private String patientName;
    private List<Integer> patientSymptoms;
    private Gender gender;
    private String yearOfBirth;
    @Id
    private String id;
}
