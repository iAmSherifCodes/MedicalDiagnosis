package com.example.diagnose.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MedicalSpecialisation {
    private Long id;
    private String name;
    @JsonProperty("SpecialistID")
    private Long specialistId;
}
