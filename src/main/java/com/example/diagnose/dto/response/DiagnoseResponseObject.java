package com.example.diagnose.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class DiagnoseResponseObject {
    private List<DiagnoseResult> diagnoseResults = new ArrayList<>();
}
