package com.example.diagnose.services;

import com.example.diagnose.dto.request.DiagnoseRequest;
import com.example.diagnose.dto.response.DiagnoseResult;

public interface UserService {
    DiagnoseResult diagnose(DiagnoseRequest diagnoseRequest);
}
