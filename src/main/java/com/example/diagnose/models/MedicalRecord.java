package com.example.diagnose.models;

import com.example.diagnose.constants.MedicalRecordStatus;
import com.example.diagnose.dto.response.DiagnoseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Getter @Setter @ToString @Document
public class MedicalRecord {
    private UUID id;
    @Indexed(unique = true)
    private String patientName;
    private List<DiagnoseResult> diagnoseResults;
    private List<Integer> symptoms;
    private MedicalRecordStatus medicalRecordStatus;
}
