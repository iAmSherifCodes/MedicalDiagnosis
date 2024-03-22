package com.example.diagnose.dto.response;

import com.example.diagnose.constants.ResultStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class DiagnoseResult {
    @JsonProperty("Issue")
    private Issue issue;
    @JsonProperty("Specialisation")
    private List<MedicalSpecialisation> specialisation;
}
