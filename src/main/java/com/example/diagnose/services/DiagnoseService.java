package com.example.diagnose.services;

import com.example.diagnose.dto.request.ValidateStatusRequest;
import com.example.diagnose.dto.response.DiagnoseResult;
import com.example.diagnose.dto.response.ValidateResultResponse;

import java.util.List;

public interface DiagnoseService {
    ValidateResultResponse validateDiagnoseResult(ValidateStatusRequest validateStatusRequest);
    List<DiagnoseResult> searchResults();
}
