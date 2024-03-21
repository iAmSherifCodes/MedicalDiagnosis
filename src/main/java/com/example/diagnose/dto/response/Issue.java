package com.example.diagnose.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter @Setter
public class Issue {
    @Id
    private Long id;
    private String name;
    private Long accuracy;
    private String icd;
    private String icdName;
    private String profName;
    private Long ranking;

}
