package com.example.diagnose.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Document
public class User {
    @Indexed(unique = true)
    private String patientName;
    private List<MedicalRecord> medicalRecords = new ArrayList<>();
    @Id
    private String id;
}
