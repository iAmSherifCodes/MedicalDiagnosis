package com.example.diagnose.dto.request;

import com.example.diagnose.constants.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class DiagnoseRequest {
    private List<Integer> symptoms;
    private Gender gender;
    private String patientName;
    private String yearOfBirth;
}
