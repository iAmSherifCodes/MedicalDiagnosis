package com.example.diagnose.dto.request;

import com.example.diagnose.constants.ResultStatus;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ValidateStatusRequest {
    private ResultStatus status;
}
