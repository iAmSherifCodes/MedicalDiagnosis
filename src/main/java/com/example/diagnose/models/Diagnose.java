package com.example.diagnose.models;

import com.example.diagnose.constants.ResultStatus;
import com.example.diagnose.dto.response.Issue;
import com.example.diagnose.dto.response.MedicalSpecialisation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter @Getter @Document
public class Diagnose {
    @Id
    private String id;
    private User patientId;
    private ResultStatus resultStatus;
    private MedicalSpecialisation medicalSpecialisation;
    private Issue issue;
}
