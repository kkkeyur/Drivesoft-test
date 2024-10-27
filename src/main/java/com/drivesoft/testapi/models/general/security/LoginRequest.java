package com.drivesoft.testapi.models.general.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
