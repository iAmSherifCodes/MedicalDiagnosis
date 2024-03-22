package com.example.diagnose.services;

import com.example.diagnose.dto.request.DiagnoseRequest;
import com.example.diagnose.dto.response.DiagnoseResult;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<DiagnoseResult> diagnose(DiagnoseRequest diagnoseRequest) throws IOException;
}
