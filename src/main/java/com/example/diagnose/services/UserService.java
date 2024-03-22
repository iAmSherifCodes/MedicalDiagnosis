package com.example.diagnose.services;

import com.example.diagnose.dto.request.DiagnoseRequest;
import com.example.diagnose.dto.request.ValidateStatusRequest;
import com.example.diagnose.dto.response.DiagnoseResponseObject;
import com.example.diagnose.dto.response.DiagnoseResult;
import com.example.diagnose.dto.response.ValidateResultResponse;
import com.example.diagnose.models.MedicalRecord;

import java.io.IOException;
import java.util.List;

public interface UserService {
    DiagnoseResponseObject diagnose(DiagnoseRequest diagnoseRequest) throws IOException;
    ValidateResultResponse validateDiagnoseResult(ValidateStatusRequest validateStatusRequest);
    List<MedicalRecord> searchResults(String userId);

}
