package com.example.diagnose.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter @Setter @ToString
public class MedicalSpecialisation {
    @JsonProperty("ID")
    private Long id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("SpecialistID")
    private Long specialistId;
}
