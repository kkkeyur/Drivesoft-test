package com.drivesoft.testapi.models.general;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@ToString
public class ApiResponse {

    private int status;
    private String message;
    private Object data;

}
