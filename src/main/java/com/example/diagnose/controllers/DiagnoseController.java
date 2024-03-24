package com.example.diagnose.controllers;

import com.example.diagnose.dto.request.DiagnoseRequest;
import com.example.diagnose.dto.request.ValidateStatusRequest;
import com.example.diagnose.dto.response.DiagnoseResponseObject;
import com.example.diagnose.dto.response.ValidateResultResponse;
import com.example.diagnose.exceptions.NotFoundException;
import com.example.diagnose.models.MedicalRecord;
import com.example.diagnose.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController @RequestMapping("/v2") @CrossOrigin("*") @AllArgsConstructor
public class DiagnoseController {

    private final UserService userService;

    @PostMapping("/diagnose")
    public ResponseEntity<DiagnoseResponseObject> diagnose(@RequestBody DiagnoseRequest diagnoseRequest){
        try{
            return new ResponseEntity<>(userService.diagnose(diagnoseRequest), HttpStatus.CREATED);
        }catch(RuntimeException | IOException e){
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/validate")
    public ResponseEntity<ValidateResultResponse> validateDiagnoseResult(@RequestBody ValidateStatusRequest validateStatusRequest){
        try{
            return new ResponseEntity<>(userService.validateDiagnoseResult(validateStatusRequest), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/results/{patientName}")
    public ResponseEntity<List<MedicalRecord>> searchDiagnosisResults(@PathVariable String patientName){
        return new ResponseEntity<>(userService.searchResults(patientName), HttpStatus.OK);
    }
}
