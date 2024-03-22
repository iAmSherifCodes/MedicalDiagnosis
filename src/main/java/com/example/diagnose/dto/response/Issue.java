package com.example.diagnose.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter @Setter @ToString
public class Issue {
    @JsonProperty("ID")
    private Long id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Accuracy")
    private Long accuracy;
    @JsonProperty("Icd")
    private String icd;
    @JsonProperty("IcdName")
    private String icdName;
    @JsonProperty("ProfName")
    private String profName;
    @JsonProperty("Ranking")
    private Long ranking;

}
