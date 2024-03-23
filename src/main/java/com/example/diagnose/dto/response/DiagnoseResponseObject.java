package com.example.diagnose.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter @Setter @ToString
public class DiagnoseResponseObject {
    private String userId;
    private String medicalRecordId;
    private List<DiagnoseResult> diagnoseResults;
}
