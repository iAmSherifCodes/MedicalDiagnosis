package com.example.diagnose.dto.response;

import com.example.diagnose.constants.ResultStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DiagnoseResult {
    @JsonProperty("Issue")
    private Issue issue;
    @JsonProperty("Specialisation")
    private List<MedicalSpecialisation> specialisation;
//    private String patientName;
//    private ResultStatus resultStatus;
//    private Object specialistId;
//    private Object issue;
}
