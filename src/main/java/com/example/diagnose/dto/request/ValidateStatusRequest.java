package com.example.diagnose.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ValidateStatusRequest {
    private String patientName;
    private String medicalRecordId;
    private String status;
}
