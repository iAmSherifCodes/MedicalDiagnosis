package com.example.diagnose.dto.request;

import com.example.diagnose.constants.MedicalRecordStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter @Getter
public class ValidateStatusRequest {
    private String patientName;
    private UUID medicalRecordId;
    private String status;
}
