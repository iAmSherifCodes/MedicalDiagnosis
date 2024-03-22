package com.example.diagnose.models;

import com.example.diagnose.dto.response.DiagnoseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter @Setter @ToString @Document
public class MedicalRecord {
    @Id
    private String id;
    private List<DiagnoseResult> diagnoseResults;
    private List<Integer> symptoms;
}
